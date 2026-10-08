package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.unwrap
import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.util.toJson
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

class MingwCryptoManagerTest : CallbackTest() {

    @Test
    fun shouldInitializeLibsodium() = runTest {
        // Given
        val cryptoManager = CryptoManager // Initialize

        // When
        val result = LibsodiumInitializer.isInitialized()

        // Then
        assertTrue { result == true }
    }

    @Test
    fun shouldGenerateEncodedCryptoKeyPair() = runTest {
        // Given
        val cryptoManager = CryptoManager // Initialize

        // When
        val result = callbackApiCall<ResultData<String?>> { cryptoManager.generateEncodedKeyPair(callback = TestCallback) }.unwrap()

        // Then
        assertFalse(result.isNullOrEmpty())
    }

    @Test
    fun shouldGenerateKeyPairFromJavaBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = callbackApiCall<ResultData<String?>> {
            cryptoManager.generateEncodedKeyPairFromEncodedBytes(
                EncodedKeyPair(publicKey = JAVA_PUBLIC_KEY, privateKey = JAVA_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()!!
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
        val result = callbackApiCall<ResultData<String?>> {
            cryptoManager.generateEncodedKeyPairFromEncodedBytes(
                EncodedKeyPair(publicKey = SODIUM_PUBLIC_KEY, privateKey = SODIUM_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()!!
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
        val result = callbackApiCall<ResultData<String?>> {
            cryptoManager.generateEncodedKeyPairFromEncodedBytes(
                EncodedKeyPair(publicKey = CRYPTO_KIT_PUBLIC_KEY, privateKey = CRYPTO_KIT_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()!!
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
        val result = callbackApiCall<ResultData<String?>> {
            cryptoManager.generateEncodedKeyPairFromEncodedBytes(
                EncodedKeyPair(publicKey = BOUNCY_CASTLE_PUBLIC_KEY, privateKey = BOUNCY_CASTLE_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()!!
            .fromJson<EncodedKeyPair>()

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.publicKey.decodeBase64ToByteArray(), result.privateKey.decodeBase64ToByteArray())
        }
    }
}