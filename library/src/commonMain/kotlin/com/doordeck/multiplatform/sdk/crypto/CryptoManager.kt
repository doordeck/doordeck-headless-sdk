package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.api.model.Crypto

expect object CryptoManager {
    fun generateKeyPair(): Crypto.KeyPair
    fun generateEncodedKeyPair(): String
    internal fun ByteArray.toPlatformPrivateKey(): ByteArray
    internal fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray
}