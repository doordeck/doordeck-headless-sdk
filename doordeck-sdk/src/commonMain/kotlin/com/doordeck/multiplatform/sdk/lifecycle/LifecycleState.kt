package com.doordeck.multiplatform.sdk.lifecycle

internal enum class LifecycleState {

    /**
     * The SDK is being initialized.
     */
    STARTING,

    /**
     * The SDK has been initialized and is ready to be used.
     */
    STARTED,

    /**
     * The SDK is releasing its resources.
     */
    STOPPING,

    /**
     * The SDK has released its resources and must be initialized again before being used.
     */
    STOPPED
}