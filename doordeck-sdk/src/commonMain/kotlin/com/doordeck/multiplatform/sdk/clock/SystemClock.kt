package com.doordeck.multiplatform.sdk.clock

import kotlin.concurrent.Volatile
import kotlin.jvm.JvmSynthetic
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant

/**
 * A skew-aware clock used by the SDK to produce timestamps that are aligned with the
 * backend rather than relying solely on the (potentially incorrect) device clock.
 *
 * Signed operations - are signed with `nbf`/`iat`/`exp` claims that the
 * backend validates against its own clock. If the device time drifts, these operations are
 * rejected. [ServerTimeSynchronizer] periodically queries the server `/time` endpoint and stores
 * the difference here as a [skew] offset, which is then applied to every timestamp produced by
 * this clock.
 */
internal object SystemClock {

    /**
     * The offset between the backend clock and the device clock (`server - device`).
     * A positive value means the device clock is running behind the server.
     */
    @Volatile
    private var skew: Duration = Duration.ZERO

    /**
     * Returns the current time corrected by the most recently measured [skew].
     */
    @JvmSynthetic
    internal fun now(): Instant = Clock.System.now() + skew

    /**
     * Returns the currently applied clock [skew].
     */
    @JvmSynthetic
    internal fun getSkew(): Duration = skew

    /**
     * Updates the clock [skew]. Called by [ServerTimeSynchronizer] after each successful sync.
     */
    @JvmSynthetic
    internal fun setSkew(skew: Duration) {
        this.skew = skew
    }

    /**
     * Resets the [skew] back to zero. Called when the SDK is released.
     */
    @JvmSynthetic
    internal fun reset() {
        skew = Duration.ZERO
    }
}