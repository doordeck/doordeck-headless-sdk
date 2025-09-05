package com.doordeck.multiplatform.sdk.config

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomNullable
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUri
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SdkConfigTest {

    @Test
    fun shouldBuildSdkConfig () = runTest {
        // Given
        val sdkConfig = SdkConfig(
            apiEnvironment = randomNullable { ApiEnvironment.entries.random() },
            cloudAuthToken = randomNullable { randomString() },
            cloudRefreshToken = randomNullable { randomString() },
            fusionHost = randomNullable { randomUri() },
            secureStorage = DefaultSecureStorage(MemorySettings()),
            debugLogging = randomNullable { randomBoolean() }
        )

        // When
        val result = SdkConfig.Builder()
            .setApiEnvironment(sdkConfig.apiEnvironment)
            .setCloudAuthToken(sdkConfig.cloudAuthToken)
            .setCloudRefreshToken(sdkConfig.cloudRefreshToken)
            .setFusionHost(sdkConfig.fusionHost)
            .setSecureStorageOverride(sdkConfig.secureStorage)
            .setDebugLogging(sdkConfig.debugLogging)
            .build()

        // Then
        assertEquals(sdkConfig, result)
    }
}