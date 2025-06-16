package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.isCertificateAboutToExpire
import com.doordeck.multiplatform.sdk.util.toJson
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import com.ionspin.kotlin.crypto.signature.Signature
import io.ktor.utils.io.core.toByteArray

/**
 * Platform-specific implementation of [CryptoManager].
 * Provides cryptographic operations using the Ed25519 algorithm with Libsodium.
 */
actual object CryptoManager {

    init {
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initializeWithCallback {
                SdkLogger.d("Successfully initialized Libsodium")
            }
        }
    }

    /**
     * @see [CryptoManager.generateKeyPair]
     */
    actual fun generateKeyPair(): Crypto.KeyPair {
        val keyPair = Signature.keypair()
        return Crypto.KeyPair(
            private = keyPair.secretKey.toByteArray(),
            public = keyPair.publicKey.toByteArray()
        )
    }

    /**
     * @see [CryptoManager.generateEncodedKeyPair]
     */
    @CName("generateEncodedKeyPair")
    actual fun generateEncodedKeyPair(): String {
        val keyPair = generateKeyPair()
        return Crypto.EncodedKeyPair(
            private = keyPair.private.encodeByteArrayToBase64(),
            public = keyPair.public.encodeByteArrayToBase64()
        ).toJson()
    }

    /**
     * @see [CryptoManager.isCertificateInvalidOrExpired]
     */
    actual fun isCertificateInvalidOrExpired(base64Certificate: String): Boolean {
        return base64Certificate.isCertificateAboutToExpire() // Fallback
    }

    /**
     * @see [CryptoManager.toPlatformPublicKey]
     */
    @Suppress("DUPLICATE_LABEL_IN_WHEN")
    internal actual fun ByteArray.toPlatformPublicKey(): ByteArray = when(size) {
        CRYPTO_KIT_PUBLIC_KEY_SIZE,
        SODIUM_PUBLIC_KEY_SIZE -> this
        JAVA_PKCS8_PUBLIC_KEY_SIZE,
        BOUNCY_CASTLE_PUBLIC_KEY_SIZE -> sliceArray(size - RAW_KEY_SIZE until size)
        else -> throw SdkException("Unknown public key size: $size")
    }

    /**
     * @see [CryptoManager.toPlatformPrivateKey]
     */
    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray = when (size) {
        CRYPTO_KIT_PRIVATE_KEY_SIZE -> Signature.seedKeypair(toUByteArray()).secretKey.toByteArray()
        SODIUM_PRIVATE_KEY_SIZE -> this
        BOUNCY_CASTLE_PRIVATE_KEY_SIZE -> Signature.seedKeypair(sliceArray(PRIVATE_KEY_ASN1_HEADER.size until JAVA_PKCS8_PRIVATE_KEY_SIZE).toUByteArray()).secretKey.toByteArray()
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> Signature.seedKeypair(sliceArray(size - RAW_KEY_SIZE until size).toUByteArray()).secretKey.toByteArray()
        else -> throw SdkException("Unknown private key size: $size")
    }

    /**
     * @see [CryptoManager.signWithPrivateKey]
     */
    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
        Signature.detached(
            message = toByteArray().toUByteArray(),
            secretKey = privateKey.toPlatformPrivateKey().toUByteArray()
        ).toByteArray()
    } catch (exception: Exception) {
        throw SdkException("Failed to sign with private key", exception)
    }

    /**
     * @see [CryptoManager.verifySignature]
     */
    internal actual fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean = try {
        Signature.verifyDetached(
            signature = toUByteArray(),
            message = message.toByteArray().toUByteArray(),
            publicKey = publicKey.toPlatformPublicKey().toUByteArray()
        )
        true
    } catch (exception: Exception) {
        SdkLogger.e(exception) { "Failed to verify signature" }
        false
    }
}