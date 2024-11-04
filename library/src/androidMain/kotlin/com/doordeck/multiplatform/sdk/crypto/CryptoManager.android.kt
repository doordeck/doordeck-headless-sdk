package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.SdkException
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
        if (size == SODIUM_PRIVATE_KEY_SIZE || size == CRYPTO_KIT_PRIVATE_KEY_SIZE) {
            val key = sliceArray(0 until PRIVATE_KEY_SIZE)
            return KEY_ASN1_HEADER + key
        }
        return this
    }

    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
        Signature.getInstance(ALGORITHM).apply {
            initSign(KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(PKCS8EncodedKeySpec(privateKey.toPlatformPrivateKey())))
            update(toByteArray())
        }.sign()
    } catch (exception: Exception) {
        throw SdkException("Failed to sign with private key", exception)
    }
}