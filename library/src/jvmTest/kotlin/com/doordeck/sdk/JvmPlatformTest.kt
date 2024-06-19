package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import io.ktor.client.engine.apache.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JvmPlatformTest {
    @Test
    fun `test platform engine`() {
        // Given
        val client = createHttpClient(ApiEnvironment.DEV, "", "")

        // When
        assertTrue { client.engine.config is ApacheEngineConfig }
    }

    @Test
    fun `test platform type`() {
        // Given
        val platform = getPlatform()

        // When
        assertEquals(platform, PlatformType.JVM)
    }
}