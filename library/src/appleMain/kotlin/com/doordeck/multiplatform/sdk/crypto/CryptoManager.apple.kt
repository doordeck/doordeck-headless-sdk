package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.SdkException
import com.doordeck.multiplatform.sdk.api.model.Crypto
import com.doordeck.multiplatform.sdk.kcrypto.KCrypto
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.*
import platform.posix.memcpy

actual object CryptoManager {

    actual fun generateKeyPair(): Crypto.KeyPair {
        val key = KCrypto.generateKeyPair()
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

    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray {
        if (this.size == SODIUM_PRIVATE_KEY_SIZE) {
            return (KCrypto.seedKeypair(sliceArray(0 until 32).toNSData()) as NSData).toByteArray()
        } else if (size == JAVA_PKCS8_PRIVATE_KEY_SIZE) {
            return (KCrypto.seedKeypair(sliceArray(size - 32 until size).toNSData()) as NSData).toByteArray()
        }
        return this
    }

    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray {
        return KCrypto.signWithPrivateKey(this, privateKey.toPlatformPrivateKey().toNSData())?.toByteArray()
            ?: throw SdkException("Failed to sign with private key")
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