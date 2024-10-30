package com.doordeck.multiplatform.sdk.crypto

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

    internal actual fun signWithPrivateKey(content: String, privateKey: ByteArray): ByteArray {
        return KCrypto.signWithPrivateKey(content, privateKey.toNSData())?.toByteArray()
            ?: error("Signature is null")
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