package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.assertDoesNotThrow
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CryptoManagerTest {

    private val JAVA_PRIVATE_KEY = "MC4CAQAwBQYDK2VwBCIEINbXN/9rPGQ1RvKOt693W9Yux6QxbwYWeAF97n2lWSvb" // 48
    private val LIBSODIUM_PRIVATE_KEY = "Q7HNk4W6f1HjEbUjpCM1g6W1Te20Zs27gnPVprIaOsR4qtCq3fkamB+miQ06zG+A64Y7BIrI5RhI/FEHbKIi1A==" // 64
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

    @Test
    fun shouldSignWithJavaPrivateKey() = runTest {
        // Given
        val signed = "EzLHK2cCKTTA2gImXcx+lbOTITekJeU/TDdZDVWcGz/R+fD0YW+F2rFDg2R/rvhC+Nsl0bWIKBVKqhkZLnK4BA=="
        val content = "hello"

        // Then
        val result = content.signWithPrivateKey(JAVA_PRIVATE_KEY.decodeBase64ToByteArray()).encodeByteArrayToBase64()

        // Then
        assertEquals(signed, result)
    }

    @Test
    fun shouldSignWithLibsodiumPrivateKey() = runTest {
        // Given
        val signed = "XM2po4r/pJXUTcr77VabfHiC/1S7N/srtKc/ydUEMqkmGXMYeMovEL6sb4j0lcQOqC9U43ETQJHbCVqhx3jlCA=="
        val content = "hello"

        // When
        val result = content.signWithPrivateKey(LIBSODIUM_PRIVATE_KEY.decodeBase64ToByteArray()).encodeByteArrayToBase64()

        // Then
        assertEquals(signed, result)
    }

    @Test
    fun shouldSignWithCryptoKitPrivateKey() = runTest {
        // Given
        val signed = "4SM7jgRkxPodsnLYjPZJUXmTA2ZscDUAeI5wKOZpJ5dKLVzCO8vR35TQaq2lY8KBPjr1kHDsqpYzTcNbDIPPAA=="
        val content = "hello"

        // When
        val result = content.signWithPrivateKey(CRYPTO_KIT_PRIVATE_KEY.decodeBase64ToByteArray()).encodeByteArrayToBase64()

        // Then
        assertEquals(signed, result)
    }
}