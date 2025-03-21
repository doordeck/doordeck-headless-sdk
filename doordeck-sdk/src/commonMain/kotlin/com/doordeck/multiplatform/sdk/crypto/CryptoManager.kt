package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.model.data.Crypto
import kotlin.time.Duration.Companion.days

internal const val SODIUM_PUBLIC_KEY_SIZE = 32
internal const val SODIUM_PRIVATE_KEY_SIZE = 64
internal const val CRYPTO_KIT_PUBLIC_KEY_SIZE = 32
internal const val CRYPTO_KIT_PRIVATE_KEY_SIZE = 32
internal const val JAVA_PKCS8_PUBLIC_KEY_SIZE = 44
internal const val JAVA_PKCS8_PRIVATE_KEY_SIZE = 48
internal const val BOUNCY_CASTLE_PUBLIC_KEY_SIZE = 44
internal const val BOUNCY_CASTLE_PRIVATE_KEY_SIZE = 83
internal const val RAW_KEY_SIZE = 32
internal val MIN_CERTIFICATE_LIFETIME_DAYS = 7.days

internal val PRIVATE_KEY_ASN1_HEADER = byteArrayOf(
    0x30, 0x2e,                     // ASN.1 SEQUENCE, length 46
    0x02, 0x01, 0x00,               // INTEGER 0
    0x30, 0x05,                     // ASN.1 SEQUENCE, length 5
    0x06, 0x03, 0x2b, 0x65, 0x70,   // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
    0x04, 0x22,                     // OCTET STRING, length 34
    0x04, 0x20                      // OCTET STRING, length 32 (your key here)
)

internal val PUBLIC_KEY_ASN1_HEADER = byteArrayOf(
    0x30, 0x2a,                     // ASN.1 SEQUENCE, length 42
    0x30, 0x05,                     // ASN.1 SEQUENCE, length 5
    0x06, 0x03, 0x2b, 0x65, 0x70,   // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
    0x03, 0x21, 0x00                // BIT STRING, length 33 (0 padding bit)
)

expect object CryptoManager {
    fun generateKeyPair(): Crypto.KeyPair
    fun generateEncodedKeyPair(): String
    fun isCertificateAboutToExpire(base64Certificate: String): Boolean
    internal fun ByteArray.toPlatformPublicKey(): ByteArray
    internal fun ByteArray.toPlatformPrivateKey(): ByteArray
    internal fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray
    internal fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean
}