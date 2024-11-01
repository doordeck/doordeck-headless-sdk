package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.assertDoesNotThrow
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class CryptoManagerTest {

    private val JAVA_PRIVATE_KEY = "MC4CAQAwBQYDK2VwBCIEINbXN/9rPGQ1RvKOt693W9Yux6QxbwYWeAF97n2lWSvb" // 48
    private val LIBSODIUM_PRIVATE_KEY = "izQOPN7WzjnlZWw8zmqfXwKxuakquHM8GW1rrbIG5tkXRU0gonCFGo8IS5dMmBk71Ph0K7ksmNxRzZQIJB2dDQ==" // 64
    private val CRYPTO_KIT_PRIVATE_KEY = "UDJNQXe8JXbt5tkLjCM8R9U9cSHh8builPRX4JwCtqs=" // 32

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
            content.signWithPrivateKey(keyPair.private)
        }
    }

    /*@Test
    fun shouldSignWithJavaPrivateKey() = runTest {
        // Given
        val content = "content"

        // Then
        assertDoesNotThrow {
            content.signWithPrivateKey(JAVA_PRIVATE_KEY.decodeBase64ToByteArray())
        }
    }*/

    @Test
    fun shouldSignWithLibsodiumPrivateKey() = runTest {
        // Given
        val content = "content"

        // Then
        assertDoesNotThrow {
            content.signWithPrivateKey(LIBSODIUM_PRIVATE_KEY.decodeBase64ToByteArray())
        }
    }

    /*@Test
    fun shouldSignWithCryptoKitPrivateKey() = runTest {
        // Given
        val content = "content"

        // Then
        assertDoesNotThrow {
            content.signWithPrivateKey(CRYPTO_KIT_PRIVATE_KEY.decodeBase64ToByteArray())
        }
    }*/
}