package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.clock.ServerTimeSynchronizer
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.config.BasicSdkConfig
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.jvm.JvmSynthetic

internal object DoordeckFactory {

    /**
     * Initializes the SDK with the provided [sdkConfig]. If the initialization fails,
     * the acquired resources are released and the original exception is rethrown.
     */
    @JvmSynthetic
    internal suspend fun initialize(sdkConfig: BasicSdkConfig): Doordeck {
        SdkLogger.enableDebugLogging(sdkConfig.debugLogging ?: false)

        SdkLogger.i { "Initializing..." }
        try {
            HttpClient.initialize()
            CloudHttpClient.initialize()
            FusionHttpClient.initialize()
            // Add the provided values into the context
            Context.also { context ->
                context.setSecureStorageImpl(sdkConfig.secureStorage)
                sdkConfig.apiEnvironment?.let { context.setApiEnvironment(it) }
                sdkConfig.cloudAuthToken?.let { context.setCloudAuthToken(it) }
                sdkConfig.cloudRefreshToken?.let { context.setCloudRefreshToken(it) }
                sdkConfig.fusionHost?.let { context.setFusionHost(it) }
            }
            CryptoManager.initialize()
            ServerTimeSynchronizer.initialize()
        } catch (exception: Exception) {
            try {
                releaseResources()
            } catch (releaseException: Exception) {
                exception.addSuppressed(releaseException)
            }
            throw exception
        }
        SdkLogger.i { "Successfully initialized" }
        return DoordeckImpl
    }

    /**
     * Releases the resources held by the SDK (background tasks and HTTP clients). The stored context
     * (tokens, keys, user details) is kept: releasing the SDK does not log the user out.
     */
    @JvmSynthetic
    internal suspend fun release() {
        SdkLogger.i { "Releasing resources..." }
        releaseResources()
        SdkLogger.i { "Finished releasing resources" }
    }

    /**
     * Always runs to completion, even if the caller is canceled.
     */
    private suspend fun releaseResources() = withContext(NonCancellable) {
        ServerTimeSynchronizer.stop()
        SystemClock.reset()
        CapabilityCache.reset()
        // Close http clients concurrently
        supervisorScope {
            listOf(CloudHttpClient, FusionHttpClient, HttpClient).forEach { client ->
                launch { client.close() }
            }
        }
    }
}