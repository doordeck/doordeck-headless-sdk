package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.api.model.Crypto
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec

actual object CryptoManager {

    private const val ALGORITHM = "Ed25519"

    actual fun generateKeyPair(): Crypto.KeyPair {
        val key = KeyPairGenerator.getInstance(ALGORITHM).generateKeyPair()
        return Crypto.KeyPair(
            private = key.private.encoded,
            public = key.public.encoded
        )
    }

    actual fun generateEncodedKeyPair(): String {
        throw NotImplementedError("Use generateKeyPair() instead")
    }

    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray {
        // Libsodium
        if (size == 64) {
            val seed = sliceArray(0 until 32)  // Extract the first 32 bytes as the seed
            return byteArrayOf(
                0x30, 0x2e,                     // ASN.1 SEQUENCE, length 46
                0x02, 0x01, 0x00,               // INTEGER 0
                0x30, 0x05,                     // ASN.1 SEQUENCE, length 5
                0x06, 0x03, 0x2b, 0x65, 0x70,   // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
                0x04, 0x22,                     // OCTET STRING, length 34
                0x04, 0x20                      // OCTET STRING, length 32 (your key here)
            ) + seed
        }
        return this
    }

    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray {
        return Signature.getInstance(ALGORITHM).apply {
            initSign(KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(PKCS8EncodedKeySpec(privateKey.toPlatformPrivateKey())))
            update(toByteArray())
        }.sign()
    }
}