package com.doordeck.multiplatform.sdk.clock

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.concurrent.Volatile
import kotlin.jvm.JvmSynthetic
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant
import kotlin.time.measureTimedValue

/**
 * Background task that keeps [SystemClock] aligned with the backend.
 *
 * On [initialize] it launches a coroutine that queries the server `/time` endpoint immediately (waiting for
 * that first query to complete, up to [FIRST_SYNC_TIMEOUT]) and then
 * on every [DEFAULT_SYNC_INTERVAL], measuring the difference between the server clock and the device clock and
 * publishing it to [SystemClock] as the current skew. The `/time` endpoint does not require
 * authentication, so synchronization also works before the user has logged in.
 */
internal object ServerTimeSynchronizer {

    /**
     * The default interval between server time synchronizations.
     */
    private val DEFAULT_SYNC_INTERVAL: Duration = 15.minutes

    /**
     * The maximum time [initialize] waits for the first synchronization to complete.
     */
    private val FIRST_SYNC_TIMEOUT: Duration = 5.seconds

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Volatile
    private var job: Job? = null

    /**
     * Starts the periodic synchronization loop and waits for its first synchronization to complete, so the caller
     * can rely on an aligned [SystemClock] right away. It stops waiting after [firstSyncTimeout], leaving the loop to
     * keep synchronizing in the background. Subsequent calls are ignored while a loop is already running, so this is
     * safe to invoke on every SDK initialization.
     *
     * @param interval How often the server time should be re-checked.
     * @param firstSyncTimeout How long to wait for the first synchronization to complete.
     */
    @JvmSynthetic
    internal suspend fun initialize(
        interval: Duration = DEFAULT_SYNC_INTERVAL,
        firstSyncTimeout: Duration = FIRST_SYNC_TIMEOUT
    ) {
        if (job?.isActive == true) {
            return
        }
        val firstSynchronization = CompletableDeferred<Unit>()
        job = scope.launch {
            synchronize()
            firstSynchronization.complete(Unit)
            while (isActive) {
                delay(interval)
                synchronize()
            }
        }
        val completed = withTimeoutOrNull(firstSyncTimeout) { firstSynchronization.await() } != null
        if (!completed) {
            SdkLogger.w { "Stopped waiting for the first server time synchronization after $firstSyncTimeout" }
        }
    }

    /**
     * Stops the synchronization loop. The measured skew stored in [SystemClock] is left untouched;
     * reset it explicitly via [SystemClock.reset] if required.
     */
    @JvmSynthetic
    internal fun stop() {
        job?.cancel()
        job = null
    }

    /**
     * Performs a single synchronization: queries the server time, estimates the device time at the
     * moment the server captured its own time (the midpoint of the request round-trip, to
     * compensate for network latency), and stores the resulting skew in [SystemClock].
     *
     * Any failure is swallowed and logged so that a transient network error never crashes the loop
     * or the host application; the previously measured skew simply remains in effect.
     */
    @JvmSynthetic
    internal suspend fun synchronize() {
        try {
            val (serverEpochMilliseconds, duration) = measureTimedValue {
                HelperClient.serverTimeRequest().now
            }

            // Estimate device time at the instant the server read its clock.
            // The midpoint of the request is: end time - half the measured duration.
            val after = Clock.System.now()
            val deviceAtServer = after - duration / 2

            val serverTime = Instant.fromEpochMilliseconds(serverEpochMilliseconds)
            val skew = serverTime - deviceAtServer

            SystemClock.setSkew(skew)
        } catch (exception: Exception) {
            SdkLogger.d { "Failed to synchronize server time: ${exception.message}" }
        }
    }
}
