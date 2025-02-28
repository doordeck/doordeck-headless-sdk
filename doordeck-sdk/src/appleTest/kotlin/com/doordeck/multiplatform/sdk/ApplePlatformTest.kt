package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.config.SdkConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class IosPlatformTest {
    @Test
    fun shouldTestPlatformEngine() = runTest {
        // Given
        val client = createCloudHttpClient()

        // Then
        assertTrue { client.engine.config is DarwinClientEngineConfig }
    }

    @Test
    fun shouldTestPlatformType() = runTest {
        // Given
        val platform = getPlatform()

        // Then
        assertEquals(platform, PlatformType.APPLE)
    }

    @Test
    fun shouldInitialize() = runTest {
        // Given
        val sdkConfig = SdkConfig.Builder()
            .setApiEnvironment(TEST_ENVIRONMENT)
            .setCloudAuthToken(Uuid.random().toString())
            .setCloudRefreshToken(Uuid.random().toString())
            .build()

        // When
        val sdk = KDoordeckFactory.initialize(sdkConfig)

        // Then
        assertEquals(sdkConfig.cloudRefreshToken, sdk.contextManager().getAuthToken())
        assertEquals(sdkConfig.cloudRefreshToken, sdk.contextManager().getRefreshToken())
        assertEquals(TEST_ENVIRONMENT, sdk.contextManager().getApiEnvironment())
    }
}