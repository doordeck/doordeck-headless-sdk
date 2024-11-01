package com.doordeck.multiplatform.sdk.crypto

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
    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray {
        return Signature.detached(
            message = toByteArray().toUByteArray(),
            secretKey = privateKey.toUByteArray()
        ).toByteArray()
    }
}

@JsExport
fun crypto(): CryptoManager = CryptoManager