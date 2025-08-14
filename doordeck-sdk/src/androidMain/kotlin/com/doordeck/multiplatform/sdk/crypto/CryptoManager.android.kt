package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.datetime.Clock
import kotlinx.datetime.toKotlinInstant
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Security
import java.security.Signature
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * Platform-specific implementation of [CryptoManager].
 * Provides cryptographic operations using the Ed25519 algorithm with Bouncy Castle.
 */
actual object CryptoManager {

    private const val EDDSA_ALGORITHM = "Ed25519"
    private const val RSA_ALGORITHM = "RSA"
    private const val CERTIFICATE_TYPE = "X.509"

    @JvmSynthetic
    internal actual suspend fun initialize() {
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.addProvider(BouncyCastleProvider())
    }

    /**
     * @see [CryptoManager.generateRawKeyPair]
     */
    @JvmSynthetic
    internal actual fun generateRawKeyPair(): Crypto.KeyPair {
        val key = generateKeyPair()
        return Crypto.KeyPair(
            private = key.private.encoded,
            public = key.public.encoded
        )
    }

    fun generateKeyPair(): KeyPair = KeyPairGenerator.getInstance(EDDSA_ALGORITHM)
        .generateKeyPair()

    @JvmSynthetic
    internal fun String.toRsaPublicKey(): PublicKey = try {
        KeyFactory.getInstance(RSA_ALGORITHM)
            .generatePublic(X509EncodedKeySpec(decodeBase64ToByteArray()))
    } catch (exception: Exception) {
        throw SdkException("Failed to generate $RSA_ALGORITHM public key", exception)
    }

    @JvmSynthetic
    internal fun ByteArray.toPublicKey(): PublicKey = try {
        KeyFactory.getInstance(EDDSA_ALGORITHM)
            .generatePublic(X509EncodedKeySpec(toPlatformPublicKey()))
    } catch (exception: Exception) {
        throw SdkException("Failed to generate $EDDSA_ALGORITHM public key", exception)
    }

    @JvmSynthetic
    internal fun ByteArray.toPrivateKey(): PrivateKey = try {
        KeyFactory.getInstance(EDDSA_ALGORITHM)
            .generatePrivate(PKCS8EncodedKeySpec(toPlatformPrivateKey()))
    } catch (exception: Exception) {
        throw SdkException("Failed to generate $EDDSA_ALGORITHM private key", exception)
    }

    @JvmSynthetic
    internal fun String.toCertificate(): X509Certificate = try {
        CertificateFactory.getInstance(CERTIFICATE_TYPE)
            .generateCertificate(decodeBase64ToByteArray().inputStream()) as X509Certificate
    } catch (exception: Exception) {
        throw SdkException("Failed to generate $CERTIFICATE_TYPE certificate", exception)
    }

    /**
     * @see [CryptoManager.isCertificateInvalidOrExpired]
     */
    actual fun isCertificateInvalidOrExpired(base64Certificate: String): Boolean {
        return try {
            val certificate = base64Certificate.toCertificate()
            val notAfterInstant = certificate.notAfter?.toInstant()?.toKotlinInstant()
            if (notAfterInstant == null) {
                SdkLogger.d { "Unable to retrieve the expiration date from the certificate" }
                return true
            }
            SdkLogger.d { "Certificate expiration date is $notAfterInstant" }
            return Clock.System.now() >= notAfterInstant - MIN_CERTIFICATE_LIFETIME_DAYS
        } catch (exception: Exception) {
            SdkLogger.e(exception) { "Failed to parse the certificate" }
            true
        }
    }

    /**
     * @see [CryptoManager.toPlatformPublicKey]
     */
    @Suppress("DUPLICATE_LABEL_IN_WHEN", "KotlinConstantConditions")
    @JvmSynthetic
    internal actual fun ByteArray.toPlatformPublicKey(): ByteArray = when (size) {
        CRYPTO_KIT_PUBLIC_KEY_SIZE,
        SODIUM_PUBLIC_KEY_SIZE -> PUBLIC_KEY_ASN1_HEADER + sliceArray(0 until RAW_KEY_SIZE)
        JAVA_PKCS8_PUBLIC_KEY_SIZE,
        BOUNCY_CASTLE_PUBLIC_KEY_SIZE -> this
        else -> throw SdkException("Unknown public key size: $size")
    }

    /**
     * @see [CryptoManager.toPlatformPrivateKey]
     */
    @JvmSynthetic
    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray = when(size) {
        CRYPTO_KIT_PRIVATE_KEY_SIZE,
        SODIUM_PRIVATE_KEY_SIZE -> PRIVATE_KEY_ASN1_HEADER + sliceArray(0 until RAW_KEY_SIZE)
        BOUNCY_CASTLE_PRIVATE_KEY_SIZE -> PRIVATE_KEY_ASN1_HEADER + sliceArray(PRIVATE_KEY_ASN1_HEADER.size until JAVA_PKCS8_PRIVATE_KEY_SIZE)
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> this
        else -> throw SdkException("Unknown private key size: $size")
    }

    /**
     * @see [CryptoManager.signWithPrivateKey]
     */
    @JvmSynthetic
    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
        Signature.getInstance(EDDSA_ALGORITHM).apply {
            initSign(privateKey.toPlatformPrivateKey().toPrivateKey())
            update(toByteArray())
        }.sign()
    } catch (exception: Exception) {
        throw SdkException("Failed to sign with private key", exception)
    }

    /**
     * @see [CryptoManager.verifySignature]
     */
    @JvmSynthetic
    internal actual fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean = try {
        val signature = Signature.getInstance(EDDSA_ALGORITHM)
        signature.initVerify(publicKey.toPlatformPublicKey().toPublicKey())
        signature.update(message.toByteArray())
        signature.verify(this)
    } catch (exception: Exception) {
        SdkLogger.e(exception) { "Failed to verify signature" }
        false
    }
}