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
    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray {
        if (size == JAVA_PKCS8_PRIVATE_KEY_SIZE) {
            val key = sliceArray(size - PRIVATE_KEY_SIZE until size)
            return Signature.seedKeypair(key.toUByteArray()).secretKey.toByteArray()
        }
        if (size == CRYPTO_KIT_PRIVATE_KEY_SIZE) {
            return Signature.seedKeypair(toUByteArray()).secretKey.toByteArray()
        }
        return this
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
}

@JsExport
fun crypto(): CryptoManager = CryptoManager