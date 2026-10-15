package com.doordeck.multiplatform.sdk

import io.ktor.client.engine.js.JsClientEngineConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class JsPlatformTest {
    @Test
    fun shouldTestPlatformEngine() = runTest {
        // Given
        val client = createCloudHttpClient()

        client.use { client ->
            // Then
            assertTrue { client.engine.config is JsClientEngineConfig }
        }
    }

    @Test
    fun shouldTestPlatformType() = runTest {
        // Given
        val platform = platformType

        // Then
        assertNotEquals(PlatformType.JS, platformType)
    }
}