package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.assertDoesNotThrow
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class CryptoManagerTest {

    private val JAVA_PRIVATE_KEY = "MC4CAQAwBQYDK2VwBCIEINbXN/9rPGQ1RvKOt693W9Yux6QxbwYWeAF97n2lWSvb"
    private val LIBSODIUM_PRIVATE_KEY = "izQOPN7WzjnlZWw8zmqfXwKxuakquHM8GW1rrbIG5tkXRU0gonCFGo8IS5dMmBk71Ph0K7ksmNxRzZQIJB2dDQ=="
    private val CRYPTO_KIT_PRIVATE_KEY = "UDJNQXe8JXbt5tkLjCM8R9U9cSHh8builPRX4JwCtqs="

    @Test
    fun shouldGenerateCryptoKeyPair() = runTest {
        assertDoesNotThrow {
            CryptoManager.generateKeyPair()
        }
    }

    @Test
    fun shouldSignWithPrivateKey() = runTest {
        // Given
        val content = "content"
        val keyPair = CryptoManager.generateKeyPair()

        // Then
        assertDoesNotThrow {
            CryptoManager.signWithPrivateKey(content, keyPair.private)
        }
    }

    @Test
    fun shouldSignWithMultiplePrivateKeys() = runTest {
        val content = "content"
        CryptoManager.signWithPrivateKey(content, JAVA_PRIVATE_KEY.decodeBase64ToByteArray())
        CryptoManager.signWithPrivateKey(content, LIBSODIUM_PRIVATE_KEY.decodeBase64ToByteArray())
        CryptoManager.signWithPrivateKey(content, CRYPTO_KIT_PRIVATE_KEY.decodeBase64ToByteArray())
    }
}