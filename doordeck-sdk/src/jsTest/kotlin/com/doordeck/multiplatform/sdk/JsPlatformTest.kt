package com.doordeck.multiplatform.sdk

import io.ktor.client.engine.js.JsClientEngineConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JsPlatformTest {
    @Test
    fun shouldTestPlatformEngine() = runTest {
        // Given
        val client = createCloudHttpClient()

        // When
        assertTrue { client.engine.config is JsClientEngineConfig }
    }

    @Test
    fun shouldTestPlatformType() = runTest {
        // Given
        val platform = getPlatform()

        // When
        assertEquals(platform, PlatformType.JS)
    }
}