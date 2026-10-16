package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
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
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame

class DoordeckFactoryTest : IntegrationTest() {

    @Test
    fun shouldStoreConfigInContextWhenInitializing() = runTest {
        // Given
        val sdkConfig = BasicSdkConfig(
            apiEnvironment = ApiEnvironment.STAGING,
            cloudAuthToken = randomString(),
            fusionHost = randomUrlString(),
            secureStorage = DefaultSecureStorage(MemorySettings())
        )

        try {
            // When
            val sdk = withContext(Dispatchers.Default) {
                DoordeckFactory.initialize(sdkConfig)
            }

            // Then
            assertSame(DoordeckImpl, sdk)
            assertEquals(LifecycleState.STARTED, Lifecycle.state)
            assertEquals(sdkConfig.apiEnvironment, Context.getApiEnvironment())
            assertEquals(sdkConfig.cloudAuthToken, Context.getCloudAuthToken())
            assertEquals(sdkConfig.fusionHost, Context.getFusionHost())
        } finally {
            DoordeckFactory.release()
        }
    }

    @Test
    fun shouldCreateHttpClientsWhenInitializing() = runTest {
        try {
            // When
            withContext(Dispatchers.Default) {
                DoordeckFactory.initialize(basicSdkConfig())
            }

            // Then
            assertClientsInitialized()
        } finally {
            DoordeckFactory.release()
        }
    }

    @Test
    fun shouldReleaseHttpClientsWhenReleasing() = runTest {
        // Given
        withContext(Dispatchers.Default) {
            DoordeckFactory.initialize(basicSdkConfig())
        }
        val cloudClient = CloudHttpClient.client

        // When
        DoordeckFactory.release()

        // Then
        assertEquals(LifecycleState.STOPPED, Lifecycle.state)
        assertFalse(cloudClient.coroutineContext.job.isActive)
        assertClientsNotInitialized()
    }

    @Test
    fun shouldThrowAndKeepCurrentClientsWhenInitializingTwice() = runTest {
        try {
            // Given
            withContext(Dispatchers.Default) {
                DoordeckFactory.initialize(basicSdkConfig(apiEnvironment = ApiEnvironment.DEV))
            }
            val cloudClient = CloudHttpClient.client

            // When
            val exception = assertFailsWith<SdkException> {
                DoordeckFactory.initialize(basicSdkConfig(apiEnvironment = ApiEnvironment.STAGING))
            }

            // Then
            assertEquals("Unable to initialize, the SDK is STARTED", exception.message)
            assertEquals(LifecycleState.STARTED, Lifecycle.state)
            assertEquals(ApiEnvironment.DEV, Context.getApiEnvironment())
            assertSame(cloudClient, CloudHttpClient.client)
        } finally {
            DoordeckFactory.release()
        }
    }

    @Test
    fun shouldInitializeAgainAfterReleasing() = runTest {
        try {
            // Given
            withContext(Dispatchers.Default) {
                DoordeckFactory.initialize(basicSdkConfig(apiEnvironment = ApiEnvironment.DEV))
            }
            DoordeckFactory.release()

            // When
            withContext(Dispatchers.Default) {
                DoordeckFactory.initialize(basicSdkConfig(apiEnvironment = ApiEnvironment.STAGING))
            }

            // Then
            assertEquals(LifecycleState.STARTED, Lifecycle.state)
            assertEquals(ApiEnvironment.STAGING, Context.getApiEnvironment())
            assertClientsInitialized()
        } finally {
            DoordeckFactory.release()
        }
    }

    @Test
    fun shouldDoNothingWhenReleasingWithoutInitializing() = runTest {
        // When
        DoordeckFactory.release()

        // Then
        assertEquals(LifecycleState.STOPPED, Lifecycle.state)
    }

    @Test
    fun shouldDoNothingWhenReleasingTwice() = runTest {
        // Given
        withContext(Dispatchers.Default) {
            DoordeckFactory.initialize(basicSdkConfig())
        }
        DoordeckFactory.release()

        // When
        DoordeckFactory.release()

        // Then
        assertEquals(LifecycleState.STOPPED, Lifecycle.state)
    }

    @Test
    fun shouldThrowWhenReleasingWhileInitializing() = runTest {
        // Given
        Lifecycle.set(LifecycleState.STARTING)

        try {
            // When
            val exception = assertFailsWith<SdkException> {
                DoordeckFactory.release()
            }

            // Then
            assertEquals("Unable to release, the SDK is STARTING", exception.message)
            assertEquals(LifecycleState.STARTING, Lifecycle.state)
        } finally {
            Lifecycle.set(LifecycleState.STOPPED)
        }
    }

    @Test
    fun shouldThrowWhenInitializingWhileReleasing() = runTest {
        // Given
        Lifecycle.set(LifecycleState.STOPPING)

        try {
            // When
            val exception = assertFailsWith<SdkException> {
                DoordeckFactory.initialize(basicSdkConfig())
            }

            // Then
            assertEquals("Unable to initialize, the SDK is STOPPING", exception.message)
            assertEquals(LifecycleState.STOPPING, Lifecycle.state)
        } finally {
            Lifecycle.set(LifecycleState.STOPPED)
        }
    }

    @Test
    fun shouldReleaseResourcesAndRethrowOriginalExceptionWhenInitializationFails() = runTest {
        // Given
        val expectedException = SdkException("Storage failure")
        // Fails once the HTTP clients have been created, while checking the stored tokens
        val failingStorage = object : SecureStorage by DefaultSecureStorage(MemorySettings()) {
            override fun getCloudAuthToken(): String = throw expectedException
        }

        try {
            // When
            val exception = assertFailsWith<SdkException> {
                withContext(Dispatchers.Default) {
                    DoordeckFactory.initialize(basicSdkConfig(secureStorage = failingStorage))
                }
            }

            // Then
            assertSame(expectedException, exception)
            assertEquals(LifecycleState.STOPPED, Lifecycle.state)
            assertClientsNotInitialized()
        } finally {
            // Do not leave the failing storage behind for the next tests
            Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings()))
        }
    }

    @Test
    fun shouldInitializeAgainAfterInitializationFails() = runTest {
        // Given
        val failingStorage = object : SecureStorage by DefaultSecureStorage(MemorySettings()) {
            override fun getCloudAuthToken(): String = throw SdkException("Storage failure")
        }
        assertFailsWith<SdkException> {
            withContext(Dispatchers.Default) {
                DoordeckFactory.initialize(basicSdkConfig(secureStorage = failingStorage))
            }
        }

        try {
            // When
            withContext(Dispatchers.Default) {
                DoordeckFactory.initialize(basicSdkConfig())
            }

            // Then
            assertEquals(LifecycleState.STARTED, Lifecycle.state)
            assertClientsInitialized()
        } finally {
            DoordeckFactory.release()
        }
    }

    private fun basicSdkConfig(
        apiEnvironment: ApiEnvironment = TEST_ENVIRONMENT,
        secureStorage: SecureStorage = DefaultSecureStorage(MemorySettings())
    ) = BasicSdkConfig(
        apiEnvironment = apiEnvironment,
        fusionHost = null,
        secureStorage = secureStorage
    )

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
}