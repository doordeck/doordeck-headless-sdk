package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.internal.api.TokenManagerImpl
import io.ktor.client.engine.js.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JsPlatformTest {
    @Test
    fun testPlatformJsEngine() {
        // Given
        val client = createHttpClient(ApiEnvironment.DEV, TokenManagerImpl())

        // When
        assertTrue { client.engine.config is JsClientEngineConfig }
    }

    @Test
    fun testPlatformType() {
        // Given
        val platform = getPlatform()

        // When
        assertEquals(platform, PlatformType.JS)
    }
}