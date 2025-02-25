package com.doordeck.multiplatform.sdk.crypto

import kotlinx.coroutines.test.runTest
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Test
import java.security.Security
import kotlin.test.assertTrue

class AndroidCryptoManagerTest {

    @Test
    fun shouldUseBouncyCastleProvider() = runTest {
        // Given
        val cryptoManager = CryptoManager // Initialize

        // When
        val provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)

        // Then
        assertTrue { provider is BouncyCastleProvider }
    }
}