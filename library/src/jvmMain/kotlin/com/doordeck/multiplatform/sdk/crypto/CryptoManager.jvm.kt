package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.api.model.Crypto
import com.doordeck.multiplatform.sdk.util.Utils.wrapEd25519KeyToPkcs8
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

    internal actual fun signWithPrivateKey(content: String, privateKey: ByteArray): ByteArray {
        val key = if (privateKey.size == 64) {
            wrapEd25519KeyToPkcs8(privateKey)
        } else {
            privateKey
        }
        val pkcs8Key = KeyFactory.getInstance(ALGORITHM)
            .generatePrivate(PKCS8EncodedKeySpec(key))
        return Signature.getInstance(ALGORITHM).apply {
            initSign(pkcs8Key)
            update(content.toByteArray())
        }.sign()
    }
}