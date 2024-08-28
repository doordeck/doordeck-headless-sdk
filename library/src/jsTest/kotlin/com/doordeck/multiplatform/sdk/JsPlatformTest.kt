package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.engine.js.JsClientEngineConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JsPlatformTest {
    @Test
    fun testPlatformJsEngine() {
        // Given
        val client = createCloudHttpClient(ApiEnvironment.DEV, ContextManagerImpl())

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