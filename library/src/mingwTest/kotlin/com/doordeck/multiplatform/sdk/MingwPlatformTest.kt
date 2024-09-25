package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.engine.winhttp.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MingwPlatformTest {
    @Test
    fun `test platform engine`() = runTest {
        // Given
        val client = createCloudHttpClient(ApiEnvironment.DEV, ContextManagerImpl())

        // When
        assertTrue { client.engine.config is WinHttpClientEngineConfig }
    }

    @Test
    fun `test platform type`() = runTest {
        // Given
        val platform = getPlatform()

        // When
        assertEquals(platform, PlatformType.WINDOWS)
    }
}