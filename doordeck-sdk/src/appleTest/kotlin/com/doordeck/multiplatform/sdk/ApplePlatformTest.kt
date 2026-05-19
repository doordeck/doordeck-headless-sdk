package com.doordeck.multiplatform.sdk

import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class IosPlatformTest {
    @Test
    fun shouldTestPlatformEngine() = runTest {
        // Given
        val client = createCloudHttpClient()

        client.use { client ->
            // Then
            assertTrue { client.engine.config is DarwinClientEngineConfig }
        }
    }

    @Test
    fun shouldTestPlatformType() = runTest {
        // Given
        val platform = platformType

        // Then
        assertTrue { platform.name.startsWith("APPLE_") }
    }
}