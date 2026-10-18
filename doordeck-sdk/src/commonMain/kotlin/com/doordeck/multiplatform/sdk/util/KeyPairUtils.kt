package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.verifySignature
import kotlin.jvm.JvmSynthetic
import kotlin.uuid.Uuid

internal object KeyPairUtils {

    /**
     * Checks whether the provided key pair is valid by signing a small piece of text with [privateKey]
     * and verifying the result with [publicKey].
     *
     * Returns `false` if signing throws (malformed or unsupported private key) or if the signature does
     * not verify (keys do not belong to the same pair, or the public key is malformed).
     *
     * **Performance:** this is not meant to be called routinely. It runs a full Ed25519 sign + verify
     * round trip, which is far more expensive than a simple check.
     */
    @JvmSynthetic
    internal fun isKeyPairValid(publicKey: ByteArray, privateKey: ByteArray): Boolean {
        val text = Uuid.random().toString()
        val signature = try {
            text.signWithPrivateKey(privateKey)
        } catch (_: Exception) {
            return false
        }
        return signature.verifySignature(publicKey, text)
    }
}