package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.assertDoesNotThrow
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.verifySignature
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CryptoManagerTest {

    private val JAVA_PUBLIC_KEY = "MCowBQYDK2VwAyEA2E8RSaHnxBpnr+RGneGfpdLFhYEPVldzHx1TxuuyjD8="
    private val JAVA_PRIVATE_KEY = "MC4CAQAwBQYDK2VwBCIEIIgEWMf5XswAhA4SwRRNl8IH34+4329pQKBfwWPVtFat"

    private val SODIUM_PUBLIC_KEY = "9vG/XmIT0JxFjjWmXzd5F7XVbWLBeIPWb2qHKnjSz8o="
    private val SODIUM_PRIVATE_KEY = "OLPBsJ3zReQj2r2YNgnGMn/B8SW/U7qP9hMrd0mdP9j28b9eYhPQnEWONaZfN3kXtdVtYsF4g9ZvaocqeNLPyg=="

    private val CRYPTO_KIT_PUBLIC_KEY = "VpXka4JVjIYQ969Yqo92+x4JgwZPh0QiJIKx/3XzAxs="
    private val CRYPTO_KIT_PRIVATE_KEY = "GJsHlbSK/tdAGDL5+7QjB/aJx/AfKOWjMUGOpQ/1F9U="

    @Test
    fun shouldGenerateCryptoKeyPair() = runTest {
        assertDoesNotThrow {
            CryptoManager.generateKeyPair()
        }
    }

    @Test
    fun shouldSignWithPrivateKey() = runTest {
        // Given
        val content = "hello"
        val keyPair = CryptoManager.generateKeyPair()

        // When
        val result = content.signWithPrivateKey(keyPair.private)

        // Then
        assertTrue { result.verifySignature(keyPair.public, content) }
    }

    @Test
    fun shouldSignWithJavaPrivateKey() = runTest {
        // Given
        val content = "hello"

        // Then
        val result = content.signWithPrivateKey(JAVA_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            result.verifySignature(JAVA_PUBLIC_KEY.decodeBase64ToByteArray(), content)
        }
    }

    @Test
    fun shouldVerifyWithJavaPrivateKey() = runTest {
        // Given
        val signed = "HC/uACBwVVbDGkFm96raXEEkSB3EfgPoQw3UFA1IKgkGZdRMtpZKgP3ocg7iAvGk7ggYvO8qPn+q75ufyuP0AA=="
        val content = "hello"

        // Then
        val result = signed.decodeBase64ToByteArray().verifySignature(JAVA_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldFailToVerifyWithJavaPrivateKey() = runTest {
        // Given
        val signed = "HC/uACBwVVbDGkFm96raXEkkSB3EfgPoQw3UFA1IKgkGZdRMtpZKgP3ocg7iAvGk7ggYvO8qPn+q75ufyuP0AA=="
        val content = "hello"

        // Then
        val result = signed.decodeBase64ToByteArray().verifySignature(JAVA_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldSignWithLibsodiumPrivateKey() = runTest {
        // Given
        val content = "hello"

        // When
        val result = content.signWithPrivateKey(SODIUM_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            result.verifySignature(SODIUM_PUBLIC_KEY.decodeBase64ToByteArray(), content)
        }
    }

    @Test
    fun shouldVerifyWithLibsodiumPrivateKey() = runTest {
        // Given
        val signed = "oFCuWDPrOimX1yrptCq/6wg8SdlPJnnLwBURMhHp7wY3WSbgWbrc14+X5VS5CoRuBDbA/HvusfXx49YcUJbjCg=="
        val content = "hello"

        // When
        val result = signed.decodeBase64ToByteArray().verifySignature(SODIUM_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldFailToVerifyWithLibsodiumPrivateKey() = runTest {
        // Given
        val signed = "FFCuWDPrOimX1yrptCq/6wg8SdlPJnnLwBURMhHp7wY3WSbgWbrc14+X5VS5CoRuBDbA/HvusfXx49YcUJbjCg=="
        val content = "hello"

        // When
        val result = signed.decodeBase64ToByteArray().verifySignature(SODIUM_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldSignWithCryptoKitPrivateKey() = runTest {
        // Given
        val content = "hello"

        // When
        val result = content.signWithPrivateKey(CRYPTO_KIT_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            result.verifySignature(CRYPTO_KIT_PUBLIC_KEY.decodeBase64ToByteArray(), content)
        }
    }

    @Test
    fun shouldVerifyWithCryptoKitPrivateKey() = runTest {
        // Given
        val signed = "nIv3lnqaUTc4u4VDyujr2BmsYDAMNvwGiutRGbMTril/B8tzZHvwsWtn03SgoQ3VuzyZ2ATq0CtNsRUdoI5UAg=="
        val content = "hello"

        // When
        val result = signed.decodeBase64ToByteArray().verifySignature(CRYPTO_KIT_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldFailToVerifyWithCryptoKitPrivateKey() = runTest {
        // Given
        val signed = "IIv3lnqaUTc4u4VDyujr2BmsYDAMNvwGiutRGbMTril/B8tzZHvwsWtn03SgoQ3VuzyZ2ATq0CtNsRUdoI5UAg=="
        val content = "hello"

        // When
        val result = signed.decodeBase64ToByteArray().verifySignature(CRYPTO_KIT_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertFalse { result }
    }
}