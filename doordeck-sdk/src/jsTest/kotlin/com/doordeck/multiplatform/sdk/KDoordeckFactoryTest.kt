package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class KDoordeckFactoryTest {

    @Test
    fun shouldInitialize() = runTest {
        // Given
        val sdkConfig = SdkConfig.Builder()
            .setApiEnvironment(TEST_ENVIRONMENT.name)
            .setCloudAuthToken(randomString())
            .setCloudRefreshToken(randomString())
            .setSecureStorageOverride(DefaultSecureStorage(MemorySettings()))
            .build()

        // When
        val sdk = KDoordeckFactory.initialize(sdkConfig).await()

        // Then
        assertEquals(sdkConfig.cloudAuthToken, sdk.contextManager().getCloudAuthToken())
        assertEquals(sdkConfig.cloudRefreshToken, sdk.contextManager().getCloudRefreshToken())
        assertEquals(sdkConfig.apiEnvironment, sdk.contextManager().getApiEnvironment())
    }
}