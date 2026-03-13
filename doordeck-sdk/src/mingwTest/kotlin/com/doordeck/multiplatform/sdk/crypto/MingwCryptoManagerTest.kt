package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.TestKeyConstants.BOUNCY_CASTLE_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.BOUNCY_CASTLE_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.CRYPTO_KIT_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.CRYPTO_KIT_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.JAVA_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.JAVA_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.SODIUM_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.SODIUM_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.model.data.EncodedKeyPair
import com.doordeck.multiplatform.sdk.util.KeyPairUtils
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
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

    @Test
    fun shouldGenerateKeyPairFromJavaBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = cryptoManager
            .generateEncodedKeyPairFromEncodedBytes(JAVA_PUBLIC_KEY, JAVA_PRIVATE_KEY)
            .fromJson<EncodedKeyPair>()

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.publicKey.decodeBase64ToByteArray(), result.privateKey.decodeBase64ToByteArray())
        }
    }

    @Test
    fun shouldGenerateKeyPairFromSodiumBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = cryptoManager
            .generateEncodedKeyPairFromEncodedBytes(SODIUM_PUBLIC_KEY, SODIUM_PRIVATE_KEY)
            .fromJson<EncodedKeyPair>()

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.publicKey.decodeBase64ToByteArray(), result.privateKey.decodeBase64ToByteArray())
        }
    }

    @Test
    fun shouldGenerateKeyPairFromCryptoKitBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = cryptoManager
            .generateEncodedKeyPairFromEncodedBytes(CRYPTO_KIT_PUBLIC_KEY, CRYPTO_KIT_PRIVATE_KEY)
            .fromJson<EncodedKeyPair>()

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.publicKey.decodeBase64ToByteArray(), result.privateKey.decodeBase64ToByteArray())
        }
    }

    @Test
    fun shouldGenerateKeyPairFromBouncyCastleBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = cryptoManager
            .generateEncodedKeyPairFromEncodedBytes(BOUNCY_CASTLE_PUBLIC_KEY, BOUNCY_CASTLE_PRIVATE_KEY)
            .fromJson<EncodedKeyPair>()

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.publicKey.decodeBase64ToByteArray(), result.privateKey.decodeBase64ToByteArray())
        }
    }
}