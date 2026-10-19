package com.doordeck.multiplatform.sdk.lifecycle

import kotlin.concurrent.Volatile
import kotlin.jvm.JvmSynthetic

/**
 * Holds the global [LifecycleState] of the SDK.
 */
internal object Lifecycle {

    /**
     * Only changed by the SDK initialization and release, which are serialized, but readable from any thread.
     */
    @Volatile
    @get:JvmSynthetic
    @set:JvmSynthetic
    internal var state: LifecycleState = LifecycleState.STOPPED

    @JvmSynthetic
    internal fun set(new: LifecycleState) {
        state = new
    }
}
