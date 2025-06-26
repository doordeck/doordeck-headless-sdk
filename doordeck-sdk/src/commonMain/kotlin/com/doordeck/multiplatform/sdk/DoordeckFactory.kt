package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.logger.SdkLogger

internal object DoordeckFactory {

    fun initialize(sdkConfig: SdkConfig): Doordeck {
        // Add the provided values into the context
        ContextManagerImpl.apply {
            setSecureStorageImpl(sdkConfig.secureStorage)
            setDebugLogging(sdkConfig.debugLogging ?: false)
            sdkConfig.apiEnvironment?.let { setApiEnvironment(it) }
            sdkConfig.cloudAuthToken?.let { setCloudAuthToken(it) }
            sdkConfig.cloudRefreshToken?.let { setCloudRefreshToken(it) }
            sdkConfig.fusionHost?.let { setFusionHost(it) }
        }
        SdkLogger.d { "Successfully initialized SDK" }
        return DoordeckImpl
    }
}