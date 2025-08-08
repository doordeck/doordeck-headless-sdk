package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.logger.SdkLogger

internal object DoordeckFactory {

    suspend fun initialize(sdkConfig: SdkConfig): Doordeck {
        // Add the provided values into the context
        ContextManagerImpl.also { context ->
            context.setSecureStorageImpl(sdkConfig.secureStorage)
            context.setDebugLogging(sdkConfig.debugLogging ?: false)
            sdkConfig.apiEnvironment?.let { context.setApiEnvironment(it) }
            sdkConfig.cloudAuthToken?.let { context.setCloudAuthToken(it) }
            sdkConfig.cloudRefreshToken?.let { context.setCloudRefreshToken(it) }
            sdkConfig.fusionHost?.let { context.setFusionHost(it) }
        }
        CryptoManager.initialize()
        SdkLogger.d { "Successfully initialized SDK" }
        return DoordeckImpl
    }
}