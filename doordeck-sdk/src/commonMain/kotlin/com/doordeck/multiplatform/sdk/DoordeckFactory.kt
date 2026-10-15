package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.clock.ServerTimeSynchronizer
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.config.BasicSdkConfig
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import kotlin.jvm.JvmSynthetic

internal object DoordeckFactory {

    @JvmSynthetic
    internal suspend fun initialize(sdkConfig: BasicSdkConfig): Doordeck {
        SdkLogger.d { "Initializing..." }

        try {
            HttpClient.initialize()
            CloudHttpClient.initialize()
            FusionHttpClient.initialize()
            // Add the provided values into the context
            Context.also { context ->
                context.setSecureStorageImpl(sdkConfig.secureStorage)
                context.setDebugLogging(sdkConfig.debugLogging ?: false)
                sdkConfig.apiEnvironment?.let { context.setApiEnvironment(it) }
                sdkConfig.cloudAuthToken?.let { context.setCloudAuthToken(it) }
                sdkConfig.cloudRefreshToken?.let { context.setCloudRefreshToken(it) }
                sdkConfig.fusionHost?.let { context.setFusionHost(it) }
            }
            CryptoManager.initialize()
            ServerTimeSynchronizer.initialize()
            Context.attemptToRefreshAuthTokens()
        } catch (exception: Throwable) {
            release()
            throw exception
        }

        SdkLogger.d { "Successfully initialized SDK" }

        return DoordeckImpl
    }

    @JvmSynthetic
    internal suspend fun release() {
        SdkLogger.d { "Releasing..." }
        ServerTimeSynchronizer.stop()
        SystemClock.reset()
        CloudHttpClient.close()
        FusionHttpClient.close()
        HttpClient.close()
        CapabilityCache.reset()
        SdkLogger.d { "Successfully released SDK resources" }
    }
}