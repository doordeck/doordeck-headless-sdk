package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.config.BasicSdkConfig
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.lifecycle.Lifecycle
import com.doordeck.multiplatform.sdk.lifecycle.LifecycleState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame

class DoordeckFactoryTest {

    @Test
    fun shouldStoreConfigInContextWhenInitializing() = lifecycleTest {
        // Given
        val sdkConfig = BasicSdkConfig(
            apiEnvironment = ApiEnvironment.STAGING,
            cloudAuthToken = randomString(),
            fusionHost = randomUrlString(),
            secureStorage = DefaultSecureStorage(MemorySettings())
        )

        // When
        val sdk = DoordeckFactory.initialize(sdkConfig)

        // Then
        assertSame(DoordeckImpl, sdk)
        assertEquals(LifecycleState.STARTED, Lifecycle.state)
        assertEquals(sdkConfig.apiEnvironment, Context.getApiEnvironment())
        assertEquals(sdkConfig.cloudAuthToken, Context.getCloudAuthToken())
        assertEquals(sdkConfig.fusionHost, Context.getFusionHost())
    }

    @Test
    fun shouldCreateHttpClientsWhenInitializing() = lifecycleTest {
        // When
        DoordeckFactory.initialize(basicSdkConfig())

        // Then
        assertClientsInitialized()
    }

    @Test
    fun shouldReleaseHttpClientsWhenReleasing() = lifecycleTest {
        // Given
        DoordeckFactory.initialize(basicSdkConfig())
        val cloudClient = CloudHttpClient.client

        // When
        DoordeckFactory.release()

        // Then
        assertEquals(LifecycleState.STOPPED, Lifecycle.state)
        assertFalse(cloudClient.coroutineContext.job.isActive)
        assertClientsNotInitialized()
    }

    @Test
    fun shouldThrowAndKeepCurrentClientsWhenInitializingTwice() = lifecycleTest {
        // Given
        DoordeckFactory.initialize(basicSdkConfig(apiEnvironment = ApiEnvironment.DEV))
        val cloudClient = CloudHttpClient.client

        // When
        val exception = assertFailsWith<SdkException> {
            DoordeckFactory.initialize(basicSdkConfig(apiEnvironment = ApiEnvironment.STAGING))
        }

        // Then
        assertEquals("Unable to initialize, the SDK has already been initialized", exception.message)
        assertEquals(LifecycleState.STARTED, Lifecycle.state)
        assertEquals(ApiEnvironment.DEV, Context.getApiEnvironment())
        assertSame(cloudClient, CloudHttpClient.client)
    }

    @Test
    fun shouldInitializeAgainAfterReleasing() = lifecycleTest {
        // Given
        DoordeckFactory.initialize(basicSdkConfig(apiEnvironment = ApiEnvironment.DEV))
        DoordeckFactory.release()

        // When
        DoordeckFactory.initialize(basicSdkConfig(apiEnvironment = ApiEnvironment.STAGING))

        // Then
        assertEquals(LifecycleState.STARTED, Lifecycle.state)
        assertEquals(ApiEnvironment.STAGING, Context.getApiEnvironment())
        assertClientsInitialized()
    }

    @Test
    fun shouldDoNothingWhenReleasingWithoutInitializing() = lifecycleTest {
        // When
        DoordeckFactory.release()

        // Then
        assertEquals(LifecycleState.STOPPED, Lifecycle.state)
    }

    @Test
    fun shouldDoNothingWhenReleasingTwice() = lifecycleTest {
        // Given
        DoordeckFactory.initialize(basicSdkConfig())
        DoordeckFactory.release()

        // When
        DoordeckFactory.release()

        // Then
        assertEquals(LifecycleState.STOPPED, Lifecycle.state)
    }

    @Test
    fun shouldReleaseResourcesAndRethrowOriginalExceptionWhenInitializationFails() = lifecycleTest {
        // Given
        val expectedException = SdkException("Storage failure")

        // When
        val exception = assertFailsWith<SdkException> {
            DoordeckFactory.initialize(basicSdkConfig(secureStorage = failingStorage(expectedException)))
        }

        // Then
        assertSame(expectedException, exception)
        assertEquals(LifecycleState.STOPPED, Lifecycle.state)
        assertClientsNotInitialized()
    }

    @Test
    fun shouldReleaseResourcesAndRethrowOriginalErrorWhenInitializationFails() = lifecycleTest {
        // Given
        val expectedError = Error("Storage failure")

        // When
        val error = assertFailsWith<Error> {
            DoordeckFactory.initialize(basicSdkConfig(secureStorage = failingStorage(expectedError)))
        }

        // Then
        assertSame(expectedError, error)
        assertEquals(LifecycleState.STOPPED, Lifecycle.state)
        assertClientsNotInitialized()
    }

    @Test
    fun shouldInitializeAgainAfterInitializationFails() = lifecycleTest {
        // Given
        assertFailsWith<SdkException> {
            DoordeckFactory.initialize(basicSdkConfig(secureStorage = failingStorage(SdkException("Storage failure"))))
        }

        // When
        DoordeckFactory.initialize(basicSdkConfig())

        // Then
        assertEquals(LifecycleState.STARTED, Lifecycle.state)
        assertClientsInitialized()
    }

    private fun basicSdkConfig(
        apiEnvironment: ApiEnvironment = TEST_ENVIRONMENT,
        secureStorage: SecureStorage = DefaultSecureStorage(MemorySettings())
    ) = BasicSdkConfig(
        apiEnvironment = apiEnvironment,
        fusionHost = null,
        secureStorage = secureStorage
    )

    /**
     * Fails once the HTTP clients have been created.
     */
    private fun failingStorage(throwable: Throwable): SecureStorage =
        object : SecureStorage by DefaultSecureStorage(MemorySettings()) {
            override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
                throw throwable
            }
        }

    private fun assertClientsInitialized() {
        // Throws if any client is not initialized
        listOf(CloudHttpClient, FusionHttpClient, HttpClient).forEach { client -> client.client }
    }

    private fun assertClientsNotInitialized() {
        listOf(CloudHttpClient, FusionHttpClient, HttpClient).forEach { client ->
            val exception = assertFailsWith<SdkException> { client.client }
            assertEquals("Http client is not initialized", exception.message)
        }
    }

    /**
     * Runs a test that initializes or releases the SDK.
     *
     * - Installs throwaway clients first: initializing the SDK closes the installed clients, which must not be the
     *   shared ones reused by the integration tests.
     * - Runs on [Dispatchers.Default], otherwise the virtual time of [runTest] skips the first server time
     *   synchronization timeout.
     * - Always releases the SDK, closes the clients and resets the global state afterward, without relying on `@AfterTest`, which
     *   cannot await suspend functions on every platform.
     */
    private fun lifecycleTest(block: suspend () -> Unit): TestResult = runTest {
        CloudHttpClient.overrideClient(createCloudHttpClient())
        FusionHttpClient.overrideClient(createFusionHttpClient())
        HttpClient.overrideClient(createHttpClient())
        try {
            withContext(Dispatchers.Default) { block() }
        } finally {
            DoordeckFactory.release()
            listOf(CloudHttpClient, FusionHttpClient, HttpClient).forEach { client -> client.close() }
            Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings()))
            SystemClock.reset()
        }
    }
}