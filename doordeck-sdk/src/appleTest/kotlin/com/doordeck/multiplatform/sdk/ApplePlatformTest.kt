package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
    fun shouldInitialize() {
        // Then
        assertDoesNotThrow {
            KDoordeckFactory.initialize(TEST_ENVIRONMENT)
        }
    }
}