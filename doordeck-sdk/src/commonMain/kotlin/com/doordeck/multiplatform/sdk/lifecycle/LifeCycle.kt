package com.doordeck.multiplatform.sdk.lifecycle

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlin.jvm.JvmSynthetic

internal object LifeCycle {

    private val _state = MutableStateFlow(LifeCycleType.STOPPED)

    fun setState(expected: LifeCycleType, update: LifeCycleType): Boolean = _state.compareAndSet(expected, update)
    fun setState(newType: LifeCycleType): LifeCycleType = _state.getAndUpdate { newType }

    @get:JvmSynthetic
    internal val state: LifeCycleType
        get() = _state.value
}