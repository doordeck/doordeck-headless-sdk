package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.Crypto
import io.ktor.util.decodeBase64Bytes
import kotlinx.datetime.Clock
import kotlinx.datetime.toKotlinInstant
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

actual object CryptoManager {

    private const val ALGORITHM = "Ed25519"
    private const val CERTIFICATE_TYPE = "X.509"

    actual fun generateKeyPair(): Crypto.KeyPair {
        val key = KeyPairGenerator.getInstance(ALGORITHM).generateKeyPair()
        return Crypto.KeyPair(
            private = key.private.encoded,
            public = key.public.encoded
        )
    }

    actual fun generateEncodedKeyPair(): String {
        throw NotImplementedError("Use generateKeyPair() instead")
    }

    actual fun isCertificateAboutToExpire(base64Certificate: String): Boolean {
        return try {
            val certificateFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE)
            val certificate = certificateFactory.generateCertificate(base64Certificate.decodeBase64Bytes().inputStream()) as X509Certificate
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

    @Suppress("DUPLICATE_LABEL_IN_WHEN", "KotlinConstantConditions")
    internal actual fun ByteArray.toPlatformPublicKey(): ByteArray = when (size) {
        CRYPTO_KIT_PUBLIC_KEY_SIZE,
        SODIUM_PUBLIC_KEY_SIZE -> PUBLIC_KEY_ASN1_HEADER + sliceArray(0 until RAW_KEY_SIZE)
        JAVA_PKCS8_PUBLIC_KEY_SIZE,
        BOUNCY_CASTLE_PUBLIC_KEY_SIZE -> this
        else -> throw SdkException("Unknown public key size: $size")
    }

    internal actual fun ByteArray.toPlatformPrivateKey(): ByteArray = when(size) {
        CRYPTO_KIT_PRIVATE_KEY_SIZE,
        SODIUM_PRIVATE_KEY_SIZE -> PRIVATE_KEY_ASN1_HEADER + sliceArray(0 until RAW_KEY_SIZE)
        BOUNCY_CASTLE_PRIVATE_KEY_SIZE -> PRIVATE_KEY_ASN1_HEADER + sliceArray(PRIVATE_KEY_ASN1_HEADER.size until JAVA_PKCS8_PRIVATE_KEY_SIZE)
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> this
        else -> throw SdkException("Unknown private key size: $size")
    }

    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
        Signature.getInstance(ALGORITHM).apply {
            initSign(KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(PKCS8EncodedKeySpec(privateKey.toPlatformPrivateKey())))
            update(toByteArray())
        }.sign()
    } catch (exception: Exception) {
        throw SdkException("Failed to sign with private key", exception)
    }

    internal actual fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean = try {
        val signature = Signature.getInstance(ALGORITHM)
        signature.initVerify(KeyFactory.getInstance(ALGORITHM).generatePublic(X509EncodedKeySpec(publicKey.toPlatformPublicKey())))
        signature.update(message.toByteArray())
        signature.verify(this)
    } catch (exception: Exception) {
        SdkLogger.e(exception) { "Failed to verify signature" }
        false
    }
}