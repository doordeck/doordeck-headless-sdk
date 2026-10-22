package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.clock.ServerTimeSynchronizer
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.config.BasicSdkConfig
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.lifecycle.Lifecycle
import com.doordeck.multiplatform.sdk.lifecycle.LifecycleState
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.jvm.JvmSynthetic

internal object DoordeckFactory {

    /**
     * Serializes [initialize] and [release], so an overlapping call waits for the running one to complete instead of
     * failing. As a result, the [Lifecycle] state is always [LifecycleState.STOPPED] or [LifecycleState.STARTED]
     * once the lock is acquired.
     */
    private val lifecycleMutex = Mutex()

    /**
     * Initializes the SDK with the provided [sdkConfig]. If the initialization fails,
     * the acquired resources are released and the original exception is rethrown.
     *
     * The SDK must be released via [release] before it can be initialized again.
     *
     * @throws SdkException if the SDK has already been initialized.
     */
    @JvmSynthetic
    internal suspend fun initialize(sdkConfig: BasicSdkConfig): Doordeck = lifecycleMutex.withLock {
        if (Lifecycle.state != LifecycleState.STOPPED) {
            throw SdkException("Unable to initialize, the SDK has already been initialized")
        }
        Lifecycle.set(LifecycleState.STARTING)
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
        } catch (exception: Throwable) {
            try {
                releaseResources()
            } catch (releaseException: Throwable) {
                exception.addSuppressed(releaseException)
            } finally {
                Lifecycle.set(LifecycleState.STOPPED)
            }
            throw exception
        }
        Lifecycle.set(LifecycleState.STARTED)
        SdkLogger.i { "Successfully initialized" }
        DoordeckImpl
    }

    /**
     * Releases the resources held by the SDK (background tasks and HTTP clients). The stored context
     * (tokens, keys, user details) is kept: releasing the SDK does not log the user out.
     *
     * Releasing an SDK that has not been initialized, or has already been released, does nothing.
     */
    @JvmSynthetic
    internal suspend fun release() = lifecycleMutex.withLock {
        if (Lifecycle.state != LifecycleState.STARTED) {
            return@withLock
        }
        Lifecycle.set(LifecycleState.STOPPING)
        SdkLogger.i { "Releasing resources..." }
        try {
            releaseResources()
        } finally {
            Lifecycle.set(LifecycleState.STOPPED)
        }
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