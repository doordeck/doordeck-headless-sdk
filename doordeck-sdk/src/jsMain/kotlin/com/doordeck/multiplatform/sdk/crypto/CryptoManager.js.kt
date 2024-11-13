package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.SdkException
import com.doordeck.multiplatform.sdk.api.model.Crypto
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import com.ionspin.kotlin.crypto.signature.Signature
import io.ktor.utils.io.core.toByteArray

@JsExport
actual object CryptoManager {

    init {
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initializeWithCallback {  }
        }
    }

    actual fun generateKeyPair(): Crypto.KeyPair {
        val keyPair = Signature.keypair()
        return Crypto.KeyPair(
            private = keyPair.secretKey.toByteArray(),
            public = keyPair.publicKey.toByteArray()
        )
    }

    actual fun generateEncodedKeyPair(): String {
        throw NotImplementedError("Use generateKeyPair() instead")
    }

    @JsExport.Ignore
    internal actual fun ByteArray.toPlatformPublicKey(): ByteArray = when(size) {
        CRYPTO_KIT_PUBLIC_KEY_SIZE,
        SODIUM_PUBLIC_KEY_SIZE -> this
        JAVA_PKCS8_PUBLIC_KEY_SIZE -> sliceArray(size - RAW_KEY_SIZE until size)
        else -> throw SdkException("Unknown public key size: $size")
    }

    @JsExport.Ignore
    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray = when (size) {
        CRYPTO_KIT_PRIVATE_KEY_SIZE -> Signature.seedKeypair(toUByteArray()).secretKey.toByteArray()
        SODIUM_PRIVATE_KEY_SIZE -> this
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> Signature.seedKeypair(sliceArray(size - RAW_KEY_SIZE until size).toUByteArray()).secretKey.toByteArray()
        else -> throw SdkException("Unknown private key size: $size")
    }

    @JsExport.Ignore
    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
        Signature.detached(
            message = toByteArray().toUByteArray(),
            secretKey = privateKey.toPlatformPrivateKey().toUByteArray()
        ).toByteArray()
    } catch (exception: Exception) {
        throw SdkException("Failed to sign with private key", exception)
    }

    @JsExport.Ignore
    internal actual fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean = try {
        Signature.verifyDetached(toUByteArray(), message.toByteArray().toUByteArray(), publicKey.toPlatformPublicKey().toUByteArray())
        true
    } catch (exception: Exception) {
        false
    }
}

@JsExport
fun crypto(): CryptoManager = CryptoManager