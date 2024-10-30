package com.doordeck.multiplatform.sdk.crypto

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

    internal actual fun signWithPrivateKey(content: String, privateKey: ByteArray): ByteArray {
        return Signature.detached(
            message = content.toByteArray().toUByteArray(),
            secretKey = privateKey.toUByteArray()
        ).toByteArray()
    }
}