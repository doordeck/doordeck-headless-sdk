package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.api.model.Crypto

const val SODIUM_PRIVATE_KEY_SIZE = 64
const val CRYPTO_KIT_PRIVATE_KEY_SIZE = 32
const val JAVA_PKCS8_PRIVATE_KEY_SIZE = 48
const val PRIVATE_KEY_SIZE = 32
val KEY_ASN1_HEADER = byteArrayOf(
    0x30, 0x2e,                     // ASN.1 SEQUENCE, length 46
    0x02, 0x01, 0x00,               // INTEGER 0
    0x30, 0x05,                     // ASN.1 SEQUENCE, length 5
    0x06, 0x03, 0x2b, 0x65, 0x70,   // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
    0x04, 0x22,                     // OCTET STRING, length 34
    0x04, 0x20                      // OCTET STRING, length 32 (your key here)
)

expect object CryptoManager {
    fun generateKeyPair(): Crypto.KeyPair
    fun generateEncodedKeyPair(): String
    internal fun ByteArray.toPlatformPrivateKey(): ByteArray
    internal fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray
}