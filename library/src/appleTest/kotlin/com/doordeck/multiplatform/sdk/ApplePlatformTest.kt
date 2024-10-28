package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import com.doordeck.multiplatform.sdk.kcrypto.KCrypto
import kotlin.test.assertNotNull
import com.doordeck.multiplatform.sdk.util.toByteArray
import platform.Foundation.NSData

class IosPlatformTest {
    @Test
    fun shouldTestPlatformEngine() = runTest {
        // Given
        val client = createCloudHttpClient(ApiEnvironment.DEV, ContextManagerImpl())

        // When
        assertTrue { client.engine.config is DarwinClientEngineConfig }
    }

    @Test
    fun shouldTestPlatformType() = runTest {
        // Given
        val platform = getPlatform()

        // When
        assertEquals(platform, PlatformType.APPLE)
    }

    @Test
    fun shouldGenerateCryptoKeyPair() = runTest {
        val keyPair = KCrypto.generateKeyPair()
        val privateKeyData = keyPair["privateKey"] as NSData
        val publicKeyData = keyPair["publicKey"] as NSData

        assertNotNull(keyPair)
        assertNotNull(privateKeyData.toByteArray())
        assertNotNull(publicKeyData.toByteArray())
    }
}