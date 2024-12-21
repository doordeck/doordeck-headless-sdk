package com.doordeck.multiplatform.sdk

import android.app.Application
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import io.ktor.client.engine.okhttp.OkHttpConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class AndroidPlatformTest {
    @Test
    fun shouldTestPlatformEngine() = runTest {
        // Given
        val client = createCloudHttpClient()

        // When
        assertTrue { client.engine.config is OkHttpConfig }
    }

    @Test
    fun shouldTestPlatformType() = runTest {
        // Given
        val platform = getPlatform()

        // Then
        assertEquals(platform, PlatformType.ANDROID)
    }

    @Test
    fun shouldInitialize() = runTest {
        // Given
        val context = ApplicationContext.apply {
            set(Application())
        }
        val token = Uuid.random().toString()
        val refreshToken = Uuid.random().toString()

        // When
        val sdk = KDoordeckFactory.initialize(context, TEST_ENVIRONMENT, token, refreshToken)

        // Then
        assertEquals(token, sdk.contextManager().getAuthToken())
        assertEquals(refreshToken, sdk.contextManager().getRefreshToken())
        assertEquals(TEST_ENVIRONMENT, sdk.contextManager().getApiEnvironment())
    }
}