package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import io.ktor.client.engine.okhttp.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AndroidPlatformTest {
    @Test
    fun `test platform engine`() {
        // Given
        val client = createHttpClient(ApiEnvironment.DEV, "", "")

        // When
        assertTrue { client.engine.config is OkHttpConfig }
    }

    @Test
    fun `test platform type`() {
        // Given
        val platform = getPlatform()

        // When
        assertEquals(platform, PlatformType.ANDROID)
    }
}