package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.model.data.EncodedKeyPair
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import com.ionspin.kotlin.crypto.signature.Signature
import io.ktor.utils.io.core.toByteArray
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.pointed
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import platform.windows.CertCreateCertificateContext
import platform.windows.CertFreeCertificateContext
import platform.windows.FILETIME
import platform.windows.GetLastError
import platform.windows.PKCS_7_ASN_ENCODING
import platform.windows.X509_ASN_ENCODING
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Instant

/**
 * Platform-specific implementation of [CryptoManager].
 * Provides cryptographic operations using the Ed25519 algorithm with Libsodium.
 */
actual object CryptoManager {

    internal actual suspend fun initialize() {
        LibsodiumInitializer.initializeWithCallback {
            SdkLogger.i("Successfully initialized Libsodium")
        }
    }

    /**
     * @see [CryptoManager.generateRawKeyPair]
     */
    actual fun generateRawKeyPair(): Crypto.KeyPair {
        val keyPair = Signature.keypair()
        return Crypto.KeyPair(
            private = keyPair.secretKey.toByteArray(),
            public = keyPair.publicKey.toByteArray()
        )
    }

    /**
     * Generates a new Ed25519 key pair and returns it as a JSON-encoded string.
     *
     * @return JSON string representation of the generated key pair.
     */
    @CName("generateEncodedKeyPair")
    fun generateEncodedKeyPair(): String {
        val keyPair = generateRawKeyPair()
        return EncodedKeyPair(
            publicKey = keyPair.public.encodeByteArrayToBase64(),
            privateKey = keyPair.private.encodeByteArrayToBase64()
        ).toJson()
    }

    @CName("generateEncodedKeyPairFromEncodedBytes")
    fun generateEncodedKeyPairFromEncodedBytes(encodedPublicKey: String, encodedPrivateKey: String): String {
        return EncodedKeyPair(
            publicKey = encodedPublicKey.decodeBase64ToByteArray().toPlatformPublicKey().encodeByteArrayToBase64(),
            privateKey = encodedPrivateKey.decodeBase64ToByteArray().toPlatformPrivateKey().encodeByteArrayToBase64()
        ).toJson()
    }

    /**
     * @see [CryptoManager.isCertificateInvalidOrExpired]
     */
    actual fun isCertificateInvalidOrExpired(base64Certificate: String): Boolean {
        return try {
            val der = base64Certificate.decodeBase64ToByteArray()
            val context = der.usePinned { pinned ->
                CertCreateCertificateContext(
                    (X509_ASN_ENCODING or PKCS_7_ASN_ENCODING).toUInt(),
                    pinned.addressOf(0).reinterpret(),
                    der.size.toUInt()
                )
            }
            if (context == null) {
                SdkLogger.e { "Failed to parse the certificate: ${GetLastError()})" }
                return true
            }
            try {
                val notAfterInstant = context.pointed.pCertInfo?.pointed?.NotAfter?.toInstant()
                if (notAfterInstant == null) {
                    SdkLogger.w { "Unable to retrieve the expiration date from the certificate" }
                    return true
                }
                SdkLogger.d { "Certificate expiration date is $notAfterInstant" }
                SystemClock.now() >= notAfterInstant - MIN_CERTIFICATE_LIFETIME_DAYS
            } finally {
                CertFreeCertificateContext(context)
            }
        } catch (exception: Throwable) {
            SdkLogger.e(exception) { "Failed to parse the certificate" }
            true
        }
    }

    private val WINDOWS_EPOCH = Instant.parse("1601-01-01T00:00:00Z")

    private fun FILETIME.toInstant(): Instant {
        val ticks = (dwHighDateTime.toLong() shl 32) or dwLowDateTime.toLong()
        return WINDOWS_EPOCH + (ticks / 10).microseconds
    }

    /**
     * @see [CryptoManager.toPlatformPublicKey]
     */
    @Suppress("DUPLICATE_LABEL_IN_WHEN")
    internal actual fun ByteArray.toPlatformPublicKey(): ByteArray = when(size) {
        CRYPTO_KIT_PUBLIC_KEY_SIZE,
        SODIUM_PUBLIC_KEY_SIZE -> this
        JAVA_PKCS8_PUBLIC_KEY_SIZE,
        BOUNCY_CASTLE_PUBLIC_KEY_SIZE -> sliceArray(size - RAW_KEY_SIZE until size)
        else -> throw SdkException("Unknown public key size: $size")
    }

    /**
     * @see [CryptoManager.toPlatformPrivateKey]
     */
    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray = when (size) {
        CRYPTO_KIT_PRIVATE_KEY_SIZE -> Signature.seedKeypair(toUByteArray()).secretKey.toByteArray()
        SODIUM_PRIVATE_KEY_SIZE -> this
        BOUNCY_CASTLE_PRIVATE_KEY_SIZE -> Signature.seedKeypair(sliceArray(PRIVATE_KEY_ASN1_HEADER.size until JAVA_PKCS8_PRIVATE_KEY_SIZE).toUByteArray()).secretKey.toByteArray()
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> Signature.seedKeypair(sliceArray(size - RAW_KEY_SIZE until size).toUByteArray()).secretKey.toByteArray()
        else -> throw SdkException("Unknown private key size: $size")
    }

    /**
     * @see [CryptoManager.signWithPrivateKey]
     */
    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
        Signature.detached(
            message = toByteArray().toUByteArray(),
            secretKey = privateKey.toPlatformPrivateKey().toUByteArray()
        ).toByteArray()
    } catch (exception: Exception) {
        throw SdkException("Failed to sign with private key", exception)
    }

    /**
     * @see [CryptoManager.verifySignature]
     */
    internal actual fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean = try {
        Signature.verifyDetached(
            signature = toUByteArray(),
            message = message.toByteArray().toUByteArray(),
            publicKey = publicKey.toPlatformPublicKey().toUByteArray()
        )
        true
    } catch (exception: Exception) {
        SdkLogger.e(exception) { "Failed to verify signature" }
        false
    }
}