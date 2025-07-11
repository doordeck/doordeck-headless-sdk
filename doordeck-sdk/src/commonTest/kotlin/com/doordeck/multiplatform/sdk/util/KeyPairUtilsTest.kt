package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class KeyPairUtilsTest {

    @Test
    fun shouldCheckValidKeyPair() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()

        // When
        val result = KeyPairUtils.isKeyPairValid(keyPair.public, keyPair.private)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckInvalidKeys() = runTest {
        // Given
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()

        // When
        val result = KeyPairUtils.isKeyPairValid(publicKey, privateKey)

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckInvalidKeyPair() = runTest {
        // Given
        val publicKey = CryptoManager.generateKeyPair().public
        val privateKey = CryptoManager.generateKeyPair().private

        // When
        val result = KeyPairUtils.isKeyPairValid(publicKey, privateKey)

        // Then
        assertFalse { result }
    }
}