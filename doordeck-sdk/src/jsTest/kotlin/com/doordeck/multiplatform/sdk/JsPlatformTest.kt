package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import io.ktor.client.engine.js.JsClientEngineConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class JsPlatformTest {
    @Test
    fun shouldTestPlatformEngine() = runTest {
        // Given
        val client = createCloudHttpClient()

        // Then
        assertTrue { client.engine.config is JsClientEngineConfig }
    }

    @Test
    fun shouldTestPlatformType() = runTest {
        // Given
        val platform = getPlatform()

        // Then
        assertEquals(platform, PlatformType.JS)
    }

    @Test
    fun shouldInitialize() = runTest {
        // Given
        val token = Uuid.random().toString()
        val refreshToken = Uuid.random().toString()

        // When
        val sdk = KDoordeckFactory.initializeWithAuthAndRefreshTokens(TEST_ENVIRONMENT, token, refreshToken)

        // Then
        assertEquals(token, sdk.contextManager().getAuthToken())
        assertEquals(refreshToken, sdk.contextManager().getRefreshToken())
        assertEquals(TEST_ENVIRONMENT, sdk.contextManager().getApiEnvironment())
    }
}