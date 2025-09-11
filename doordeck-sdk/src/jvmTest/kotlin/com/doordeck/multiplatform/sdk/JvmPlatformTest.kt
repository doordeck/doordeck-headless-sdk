package com.doordeck.multiplatform.sdk

import io.ktor.client.engine.okhttp.OkHttpConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JvmPlatformTest {
    @Test
    fun shouldTestPlatformEngine() = runTest {
        // Given
        val client = createCloudHttpClient()

        // Then
        assertTrue { client.engine.config is OkHttpConfig }
    }

    @Test
    fun shouldTestPlatformType() = runTest {
        // Given
        val platform = platformType

        // Then
        assertEquals(platform, PlatformType.JVM)
    }
}