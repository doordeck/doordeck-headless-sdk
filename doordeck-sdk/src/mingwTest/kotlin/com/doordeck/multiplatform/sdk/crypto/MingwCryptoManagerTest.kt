package com.doordeck.multiplatform.sdk.crypto

import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MingwCryptoManagerTest {

    @Test
    fun shouldInitializeLibsodium() = runTest {
        // Given
        val cryptoManager = CryptoManager // Initialize

        // When
        val result = LibsodiumInitializer.isInitialized()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldGenerateEncodedCryptoKeyPair() = runTest {
        // Given
        val cryptoManager = CryptoManager // Initialize

        // When
        val result = cryptoManager.generateEncodedKeyPair()

        // Then
        assertFalse(result.isEmpty())
    }
}