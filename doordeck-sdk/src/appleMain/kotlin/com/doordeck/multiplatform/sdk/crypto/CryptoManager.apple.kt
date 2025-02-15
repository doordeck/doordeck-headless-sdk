package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.kcryptokit.KCryptoKit
import com.doordeck.multiplatform.sdk.model.Crypto
import com.doordeck.multiplatform.sdk.util.isCertificateAboutToExpire
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.*
import platform.posix.memcpy

actual object CryptoManager {

    actual fun generateKeyPair(): Crypto.KeyPair {
        val key = KCryptoKit.generateKeyPair()
        val privateKeyData = key["privateKey"] as NSData
        val publicKeyData = key["publicKey"] as NSData
        return Crypto.KeyPair(
            private = privateKeyData.toByteArray(),
            public = publicKeyData.toByteArray()
        )
    }

    actual fun generateEncodedKeyPair(): String {
        throw NotImplementedError("Use generateKeyPair() instead")
    }

    actual fun isCertificateAboutToExpire(base64Certificate: String): Boolean {
        return base64Certificate.isCertificateAboutToExpire() // Fallback
    }

    internal actual fun ByteArray.toPlatformPublicKey(): ByteArray = when(size) {
        CRYPTO_KIT_PUBLIC_KEY_SIZE,
        SODIUM_PUBLIC_KEY_SIZE -> this
        JAVA_PKCS8_PUBLIC_KEY_SIZE -> sliceArray(size - RAW_KEY_SIZE until size)
        else -> throw SdkException("Unknown public key size: $size")
    }

    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray = when (size) {
        CRYPTO_KIT_PRIVATE_KEY_SIZE -> this
        SODIUM_PRIVATE_KEY_SIZE -> sliceArray(0 until RAW_KEY_SIZE)
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> sliceArray(size - RAW_KEY_SIZE until size)
        else -> throw SdkException("Unknown private key size: $size")
    }

    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray {
        return KCryptoKit.signWithPrivateKey(this, privateKey.toPlatformPrivateKey().toNSData())?.toByteArray()
            ?: throw SdkException("Failed to sign with private key")
    }

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
    NSData.create(bytes = allocArrayOf(this@toNSData), length = this@toNSData.size.toULong())
}