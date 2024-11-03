package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.api.model.Crypto

const val SODIUM_PRIVATE_KEY_SIZE = 64
const val CRYPTO_KIT_PRIVATE_KEY_SIZE = 32
const val JAVA_PKCS8_PRIVATE_KEY_SIZE = 48

expect object CryptoManager {
    fun generateKeyPair(): Crypto.KeyPair
    fun generateEncodedKeyPair(): String
    internal fun ByteArray.toPlatformPrivateKey(): ByteArray
    internal fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray
}