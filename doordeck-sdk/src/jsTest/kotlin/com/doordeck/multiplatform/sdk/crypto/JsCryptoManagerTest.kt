package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.TestKeyConstants.BOUNCY_CASTLE_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.BOUNCY_CASTLE_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.CRYPTO_KIT_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.CRYPTO_KIT_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.JAVA_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.JAVA_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.SODIUM_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestKeyConstants.SODIUM_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.jsmodule.Sodium
import com.doordeck.multiplatform.sdk.util.KeyPairUtils
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JsCryptoManagerTest {

    @Test
    fun shouldInitializeLibsodium() = runTest {
        // Given
        val cryptoManager = CryptoManager // Initialize

        // When
        val result = Sodium.ready.get<Unit>()

        // Then
        assertEquals(Unit, result)
    }

    @Test
    fun shouldGenerateKeyPairFromJavaBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = cryptoManager.generateKeyPairFromBytes(JAVA_PUBLIC_KEY.decodeBase64ToByteArray(),
            JAVA_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.public, result.private)
        }
    }

    @Test
    fun shouldGenerateKeyPairFromSodiumBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = cryptoManager.generateKeyPairFromBytes(SODIUM_PUBLIC_KEY.decodeBase64ToByteArray(),
            SODIUM_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.public, result.private)
        }
    }

    @Test
    fun shouldGenerateKeyPairFromCryptoKitBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = cryptoManager.generateKeyPairFromBytes(CRYPTO_KIT_PUBLIC_KEY.decodeBase64ToByteArray(),
            CRYPTO_KIT_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.public, result.private)
        }
    }

    @Test
    fun shouldGenerateKeyPairFromBouncyCastleBytes() = runTest {
        // Given
        val cryptoManager = CryptoManager

        // When
        val result = cryptoManager.generateKeyPairFromBytes(BOUNCY_CASTLE_PUBLIC_KEY.decodeBase64ToByteArray(),
            BOUNCY_CASTLE_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            KeyPairUtils.isKeyPairValid(result.public, result.private)
        }
    }
}