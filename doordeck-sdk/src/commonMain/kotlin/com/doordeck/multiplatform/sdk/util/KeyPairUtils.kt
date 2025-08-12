package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.verifySignature
import kotlin.jvm.JvmSynthetic
import kotlin.uuid.Uuid

internal object KeyPairUtils {

    /**
     * Checks whether the provided key pair is valid by signing a small piece of text and verifying it.
     */
    @JvmSynthetic
    fun isKeyPairValid(publicKey: ByteArray, privateKey: ByteArray): Boolean {
        val text = Uuid.random().toString()
        val signature = try {
            text.signWithPrivateKey(privateKey)
        } catch (_: Exception) {
            return false
        }
        return signature.verifySignature(publicKey, text)
    }
}