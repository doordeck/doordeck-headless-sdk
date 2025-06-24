package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.jsmodule.ASN1
import com.doordeck.multiplatform.sdk.jsmodule.PKI
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import com.ionspin.kotlin.crypto.signature.Signature
import io.ktor.util.decodeBase64Bytes
import io.ktor.util.toJsArray
import io.ktor.utils.io.core.toByteArray
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.js.Date

/**
 * Platform-specific implementation of [CryptoManager].
 * Provides cryptographic operations using the Ed25519 algorithm with Libsodium.
 */
@JsExport
actual object CryptoManager {

    init {
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initializeWithCallback {
                SdkLogger.d("Successfully initialized Libsodium")
            }
        }
    }

    /**
     * @see [CryptoManager.generateKeyPair]
     */
    actual fun generateKeyPair(): Crypto.KeyPair {
        val keyPair = Signature.keypair()
        return Crypto.KeyPair(
            private = keyPair.secretKey.toByteArray(),
            public = keyPair.publicKey.toByteArray()
        )
    }

    /**
     * This method is not implemented and will throw [NotImplementedError]. Use [generateKeyPair] instead.
     * @throws [NotImplementedError].
     */
    actual fun generateEncodedKeyPair(): String {
        throw NotImplementedError("Use generateKeyPair() instead")
    }

    /**
     * @see [CryptoManager.isCertificateInvalidOrExpired]
     */
    actual fun isCertificateInvalidOrExpired(base64Certificate: String): Boolean = try {
        val asn1 = ASN1.fromBER(base64Certificate.decodeBase64Bytes().toJsArray().buffer)
        val certificate = PKI.Certificate()
        certificate.fromSchema(asn1.result)
        val notAfterInstant = Instant.parse(Date(certificate.notAfter.value.toString()).toISOString())
        SdkLogger.d { "Certificate expiration date is $notAfterInstant" }
        Clock.System.now() >= notAfterInstant - MIN_CERTIFICATE_LIFETIME_DAYS
    } catch (exception: Throwable) {
        SdkLogger.e(exception) { "Failed to parse the certificate" }
        true
    }

    /**
     * @see [CryptoManager.toPlatformPublicKey]
     */
    @Suppress("DUPLICATE_LABEL_IN_WHEN")
    @JsExport.Ignore
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
    @JsExport.Ignore
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
    @JsExport.Ignore
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
    @JsExport.Ignore
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

@JsExport
fun crypto(): CryptoManager = CryptoManager