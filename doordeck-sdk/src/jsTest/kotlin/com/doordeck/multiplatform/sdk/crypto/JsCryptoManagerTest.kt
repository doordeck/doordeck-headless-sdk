package com.doordeck.multiplatform.sdk.crypto

import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class JsCryptoManagerTest {

    @Test
    fun shouldInitializeLibsodium() = runTest {
        // Given
        val cryptoManager = CryptoManager // Initialize

        // When
        val result = LibsodiumInitializer.isInitialized()

        // Then
        assertTrue { result }
    }
}