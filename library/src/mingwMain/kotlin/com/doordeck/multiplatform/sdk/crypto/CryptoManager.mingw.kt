package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.SdkException
import com.doordeck.multiplatform.sdk.api.model.Crypto
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import com.ionspin.kotlin.crypto.signature.Signature
import io.ktor.utils.io.core.toByteArray

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
        val keyPair = generateKeyPair()
        return Crypto.EncodedKeyPair(
            private = keyPair.private.encodeByteArrayToBase64(),
            public = keyPair.public.encodeByteArrayToBase64()
        ).toJson()
    }

    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray {
        if (size == JAVA_PKCS8_PRIVATE_KEY_SIZE) {
            return Signature.seedKeypair(sliceArray(size - 32 until size).toUByteArray()).secretKey.toByteArray()
        }
        if (size == CRYPTO_KIT_PRIVATE_KEY_SIZE) {
            return Signature.seedKeypair(sliceArray(0 until 32).toUByteArray()).secretKey.toByteArray()
        }
        return this
    }

    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
        Signature.detached(
            message = toByteArray().toUByteArray(),
            secretKey = privateKey.toPlatformPrivateKey().toUByteArray()
        ).toByteArray()
    } catch (exception: Exception) {
        throw SdkException("Failed to sign with private key", exception)
    }
}