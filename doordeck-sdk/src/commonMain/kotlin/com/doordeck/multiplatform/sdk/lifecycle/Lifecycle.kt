package com.doordeck.multiplatform.sdk.lifecycle

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.jvm.JvmSynthetic

/**
 * Holds the global [LifecycleState] of the SDK.
 */
internal object Lifecycle {

    private val _state = MutableStateFlow(LifecycleState.STOPPED)

    @get:JvmSynthetic
    internal val state: LifecycleState
        get() = _state.value

    /**
     * Atomically moves to [to] only if the current state is [from].
     *
     * @return `true` if the transition was applied, `false` otherwise.
     */
    @JvmSynthetic
    internal fun transition(from: LifecycleState, to: LifecycleState): Boolean = _state.compareAndSet(from, to)

    @JvmSynthetic
    internal fun set(state: LifecycleState) {
        _state.value = state
    }
}
