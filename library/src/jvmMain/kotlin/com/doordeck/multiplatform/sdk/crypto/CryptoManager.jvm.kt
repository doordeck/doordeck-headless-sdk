package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.SdkException
import com.doordeck.multiplatform.sdk.api.model.Crypto
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

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

    internal actual fun ByteArray.toPlatformPublicKey(): ByteArray = when (size) {
        CRYPTO_KIT_PUBLIC_KEY_SIZE,
        SODIUM_PUBLIC_KEY_SIZE -> PUBLIC_KEY_ASN1_HEADER + sliceArray(0 until RAW_KEY_SIZE)
        JAVA_PKCS8_PUBLIC_KEY_SIZE -> this
        else -> throw SdkException("Unknown public key size: $size")
    }

    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray = when(size) {
        CRYPTO_KIT_PRIVATE_KEY_SIZE,
        SODIUM_PRIVATE_KEY_SIZE -> PRIVATE_KEY_ASN1_HEADER + sliceArray(0 until RAW_KEY_SIZE)
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> this
        else -> throw SdkException("Unknown private key size: $size")
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

    internal actual fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean = try {
        val signature = Signature.getInstance(ALGORITHM)
        signature.initVerify(KeyFactory.getInstance(ALGORITHM).generatePublic(X509EncodedKeySpec(publicKey.toPlatformPublicKey())))
        signature.update(message.toByteArray())
        signature.verify(this)
    } catch (exception: Exception) {
        false
    }
}