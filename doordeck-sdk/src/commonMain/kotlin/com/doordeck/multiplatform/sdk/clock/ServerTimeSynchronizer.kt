package com.doordeck.multiplatform.sdk.clock

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile
import kotlin.jvm.JvmSynthetic
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

/**
 * Background task that keeps [SystemClock] aligned with the backend.
 *
 * On [initialize] it launches a coroutine that queries the server `/time` endpoint immediately and then
 * on every [DEFAULT_SYNC_INTERVAL], measuring the difference between the server clock and the device clock and
 * publishing it to [SystemClock] as the current skew. The `/time` endpoint does not require
 * authentication, so synchronization also works before the user has logged in.
 */
internal object ServerTimeSynchronizer {

    /**
     * The default interval between server time synchronizations.
     */
    private val DEFAULT_SYNC_INTERVAL: Duration = 15.minutes

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Volatile
    private var job: Job? = null

    /**
     * Initializes the periodic synchronization loop. Subsequent calls are ignored while a loop is
     * already running, so this is safe to invoke on every SDK initialization.
     *
     * @param interval How often the server time should be re-checked.
     */
    @JvmSynthetic
    internal fun initialize(interval: Duration = DEFAULT_SYNC_INTERVAL) {
        if (job?.isActive == true) {
            return
        }
        job = scope.launch {
            while (isActive) {
                synchronize()
                delay(interval)
            }
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
            val before = Clock.System.now()
            val serverEpochSeconds = HelperClient.serverTimeRequest().now
            val after = Clock.System.now()

            // Estimate the device time at the instant the server read its own clock as the midpoint
            // of the round-trip, so half of the network latency is discounted from the skew.
            val deviceAtServer = before + (after - before) / 2
            val serverTime = Instant.fromEpochSeconds(serverEpochSeconds)
            val skew = serverTime - deviceAtServer

            SystemClock.setSkew(skew)
            SdkLogger.d { "Synchronized server time, applied clock skew: $skew" }
        } catch (exception: Exception) {
            SdkLogger.d { "Failed to synchronize server time: ${exception.message}" }
        }
    }
}
