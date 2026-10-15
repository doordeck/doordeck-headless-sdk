package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class KDoordeckFactoryAsyncTest {

    @Test
    fun shouldInitialize() = runTest {
        // Given
        val sdkConfig = SdkConfig.Builder()
            .setApiEnvironment(TEST_ENVIRONMENT)
            .setCloudAuthToken(randomString())
            .setCloudRefreshToken(randomString())
            .setSecureStorageOverride(DefaultSecureStorage(MemorySettings()))
            .build()

        try {
            // When
            val sdk = KDoordeckFactory.initializeAsync(sdkConfig).await()

            // Then
            assertEquals(sdkConfig.cloudAuthToken, sdk.contextManager().getCloudAuthToken())
            assertEquals(sdkConfig.cloudRefreshToken, sdk.contextManager().getCloudRefreshToken())
            assertEquals(sdkConfig.apiEnvironment, sdk.contextManager().getApiEnvironment())
        } finally {
            KDoordeckFactory.releaseAsync().await()
        }
    }

    @Test
    fun shouldReleaseHttpResourcesAsync() = runTest {
        // Given
        val config = SdkConfig.Builder()
            .setApiEnvironment(TEST_ENVIRONMENT)
            .setSecureStorageOverride(DefaultSecureStorage(MemorySettings()))
            .build()
        val sdk = KDoordeckFactory.initializeAsync(config).await()
        sdk.accountless().loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        KDoordeckFactory.releaseAsync().await()

        // Then
        val exception = assertFailsWith<SdkException> {
            sdk.accountless().loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        }
        assertEquals("Http client is not initialized", exception.message)
    }
}