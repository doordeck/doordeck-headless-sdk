package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl

internal object DoordeckFactory {

    fun initialize(sdkConfig: SdkConfig): Doordeck {
        // Add the provided values into the context
        ContextManagerImpl.also { context ->
            context.setApiEnvironment(sdkConfig.apiEnvironment)
            context.setSecureStorageImpl(sdkConfig.secureStorage)
            sdkConfig.cloudAuthToken?.let { context.setCloudAuthToken(it) }
            sdkConfig.cloudRefreshToken?.let { context.setCloudRefreshToken(it) }
            context.loadContext()
        }
        return DoordeckImpl
    }
}