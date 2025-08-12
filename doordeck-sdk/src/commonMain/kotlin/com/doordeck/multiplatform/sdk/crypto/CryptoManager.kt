package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.data.Crypto
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration.Companion.days

/**
 * Size (in bytes) of a Libsodium-formatted Ed25519 public key.
 */
@JvmSynthetic
internal const val SODIUM_PUBLIC_KEY_SIZE = 32

/**
 * Size (in bytes) of a Libsodium-formatted Ed25519 private key.
 */
@JvmSynthetic
internal const val SODIUM_PRIVATE_KEY_SIZE = 64

/**
 * Size (in bytes) of an Apple CryptoKit-formatted Ed25519 public key.
 */
@JvmSynthetic
internal const val CRYPTO_KIT_PUBLIC_KEY_SIZE = 32

/**
 * Size (in bytes) of an Apple CryptoKit-formatted Ed25519 private key.
 */
@JvmSynthetic
internal const val CRYPTO_KIT_PRIVATE_KEY_SIZE = 32

/**
 * Size (in bytes) of a standard Java PKCS#8-formatted Ed25519 public key.
 */
@JvmSynthetic
internal const val JAVA_PKCS8_PUBLIC_KEY_SIZE = 44

/**
 * Size (in bytes) of a standard Java PKCS#8-formatted Ed25519 private key.
 */
@JvmSynthetic
internal const val JAVA_PKCS8_PRIVATE_KEY_SIZE = 48

/**
 * Size (in bytes) of a Bouncy Castle-formatted Ed25519 public key.
 */
@JvmSynthetic
internal const val BOUNCY_CASTLE_PUBLIC_KEY_SIZE = 44

/**
 * Size (in bytes) of a Bouncy Castle-formatted Ed25519 private key.
 */
@JvmSynthetic
internal const val BOUNCY_CASTLE_PRIVATE_KEY_SIZE = 83

/**
 * Size (in bytes) of the raw cryptographic key material (without any formatting headers).
 */
@JvmSynthetic
internal const val RAW_KEY_SIZE = 32

/**
 * Minimum acceptable lifetime remaining for a certificate to be considered valid.
 * Certificates expiring within this period will be treated as expired.
 */
@get:JvmSynthetic
internal val MIN_CERTIFICATE_LIFETIME_DAYS = 7.days

/**
 * ASN.1 header structure for PKCS#8 formatted Ed25519 private keys.
 */
@get:JvmSynthetic
internal val PRIVATE_KEY_ASN1_HEADER = byteArrayOf(
    0x30, 0x2e,                     // ASN.1 SEQUENCE, length 46
    0x02, 0x01, 0x00,               // INTEGER 0
    0x30, 0x05,                     // ASN.1 SEQUENCE, length 5
    0x06, 0x03, 0x2b, 0x65, 0x70,   // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
    0x04, 0x22,                     // OCTET STRING, length 34
    0x04, 0x20                      // OCTET STRING, length 32 (your key here)
)

/**
 * ASN.1 header structure for X.509 formatted Ed25519 public keys.
 */
@get:JvmSynthetic
internal val PUBLIC_KEY_ASN1_HEADER = byteArrayOf(
    0x30, 0x2a,                     // ASN.1 SEQUENCE, length 42
    0x30, 0x05,                     // ASN.1 SEQUENCE, length 5
    0x06, 0x03, 0x2b, 0x65, 0x70,   // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
    0x03, 0x21, 0x00                // BIT STRING, length 33 (0 padding bit)
)

expect object CryptoManager {

    /**
     * Initializes the necessary platform-specific dependencies (if any) to handle the crypto operations.
     */
    internal suspend fun initialize()

    /**
     * Generates a new Ed25519 key pair.
     *
     * @return A [Crypto.KeyPair] containing the encoded private and public keys
     */
    internal fun generateRawKeyPair(): Crypto.KeyPair

    /**
     * Checks if a certificate is invalid (e.g., null, malformed) or expired.
     * (we consider it expired if it will expire within the next [MIN_CERTIFICATE_LIFETIME_DAYS] days).
     *
     * @param base64Certificate The certificate in base64 encoded DER format.
     * @return true if the certificate is invalid or expired; false otherwise.
     */
    fun isCertificateInvalidOrExpired(base64Certificate: String): Boolean

    /**
     * Converts a byte array representing a public key to the platform-specific format.
     *
     * @return The public key in platform-specific format.
     * @throws SdkException if the key size is unknown.
     */
    internal fun ByteArray.toPlatformPublicKey(): ByteArray

    /**
     * Converts a byte array representing a private key to the platform-specific format.
     *
     * @return The private key in platform-specific format.
     * @throws SdkException if the key size is unknown.
     */
    internal fun ByteArray.toPlatformPrivateKey(): ByteArray

    /**
     * Signs a message using the provided private key.
     *
     * @param privateKey The private key to use for signing.
     * @return The signature as a byte array.
     * @throws SdkException if signing fails.
     */
    internal fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray

    /**
     * Verifies a signature against a message using the provided public key.
     *
     * @param publicKey The public key to use for verification.
     * @param message The original message that was signed.
     * @return true if the signature is valid, false otherwise or if verification fails.
     */
    internal fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean
}