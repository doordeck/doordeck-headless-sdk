package com.doordeck.multiplatform.sdk

import android.app.Application
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
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
        val sdkConfig = SdkConfig.Builder()
            .setApiEnvironment(TEST_ENVIRONMENT)
            .setCloudAuthToken(Uuid.random().toString())
            .setCloudRefreshToken(Uuid.random().toString())
            .setApplicationContext(context)
            .setSecureStorageOverride(DefaultSecureStorage(MemorySettings()))
            .build()

        // When
        val sdk = KDoordeckFactory.initialize(sdkConfig)

        // Then
        assertEquals(sdkConfig.cloudAuthToken, sdk.contextManager().getCloudAuthToken())
        assertEquals(sdkConfig.cloudRefreshToken, sdk.contextManager().getCloudRefreshToken())
        assertEquals(sdkConfig.apiEnvironment, sdk.contextManager().getApiEnvironment())
    }
}