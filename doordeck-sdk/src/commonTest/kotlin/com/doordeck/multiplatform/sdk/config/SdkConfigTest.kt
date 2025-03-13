package com.doordeck.multiplatform.sdk.config

import com.doordeck.multiplatform.sdk.randomSdkConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SdkConfigTest {

    @Test
    fun shouldBuildSdkConfig () = runTest {
        // Given
        val sdkConfig = randomSdkConfig()

        // When
        val result = SdkConfig.Builder()
            .setApiEnvironment(sdkConfig.apiEnvironment)
            .setCloudAuthToken(sdkConfig.cloudAuthToken)
            .setCloudRefreshToken(sdkConfig.cloudRefreshToken)
            .setFusionHost(sdkConfig.fusionHost)
            .setSecureStorageOverride(sdkConfig.secureStorage)
            .build()

        // Then
        assertEquals(sdkConfig, result)
    }
}