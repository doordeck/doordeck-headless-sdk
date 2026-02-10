package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.jsmodule.Sodium
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class JsCryptoManagerTest {

    @Test
    fun shouldInitializeLibsodium() = runTest {
        // Given
        val cryptoManager = CryptoManager // Initialize

        // When
        val result = Sodium.ready.get<Boolean>()

        // Then
        assertTrue { result }
    }
}