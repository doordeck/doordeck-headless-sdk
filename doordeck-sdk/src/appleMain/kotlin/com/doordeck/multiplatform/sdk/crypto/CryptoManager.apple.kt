package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.kcryptokit.KCryptoKit
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.util.isCertificateInvalidOrExpired
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.*
import platform.darwin.NSUInteger
import platform.posix.memcpy

/**
 * Platform-specific implementation of [CryptoManager].
 * Provides cryptographic operations using the Ed25519 algorithm with standard Apple Crypto Kit APIs.
 */
actual object CryptoManager {

    /**
     * @see [CryptoManager.generateKeyPair]
     */
    actual fun generateKeyPair(): Crypto.KeyPair {
        val key = KCryptoKit.generateKeyPair()
        val privateKeyData = key["privateKey"] as NSData
        val publicKeyData = key["publicKey"] as NSData
        return Crypto.KeyPair(
            private = privateKeyData.toByteArray(),
            public = publicKeyData.toByteArray()
        )
    }

    /**
     * @see [CryptoManager.isCertificateInvalidOrExpired]
     */
    actual fun isCertificateInvalidOrExpired(base64Certificate: String): Boolean {
        return base64Certificate.isCertificateInvalidOrExpired() // Fallback
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
        CRYPTO_KIT_PRIVATE_KEY_SIZE -> this
        SODIUM_PRIVATE_KEY_SIZE -> sliceArray(0 until RAW_KEY_SIZE)
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> sliceArray(size - RAW_KEY_SIZE until size)
        BOUNCY_CASTLE_PRIVATE_KEY_SIZE -> sliceArray(PRIVATE_KEY_ASN1_HEADER.size until JAVA_PKCS8_PRIVATE_KEY_SIZE)
        else -> throw SdkException("Unknown private key size: $size")
    }

    /**
     * @see [CryptoManager.signWithPrivateKey]
     */
    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray {
        return KCryptoKit.signWithPrivateKey(this, privateKey.toPlatformPrivateKey().toNSData())?.toByteArray()
            ?: throw SdkException("Failed to sign with private key")
    }

    /**
     * @see [CryptoManager.verifySignature]
     */
    internal actual fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean {
        return KCryptoKit.verifySignature(publicKey.toPlatformPublicKey().toNSData(), message, toNSData())
    }
}

internal fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), bytes, length)
    }
}

internal fun ByteArray.toNSData(): NSData = memScoped {
    NSData.create(
        bytes = allocArrayOf(this@toNSData),
        length = this@toNSData.size.convert<NSUInteger>()
    )
}