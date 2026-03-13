package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.jsmodule.ASN1
import com.doordeck.multiplatform.sdk.jsmodule.PKI
import com.doordeck.multiplatform.sdk.jsmodule.Sodium
import com.doordeck.multiplatform.sdk.jsmodule.toByteArray
import com.doordeck.multiplatform.sdk.jsmodule.toUint8Array
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.Crypto
import io.ktor.util.decodeBase64Bytes
import io.ktor.util.toJsArray
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.await
import org.khronos.webgl.Uint8Array
import kotlin.js.Date
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Platform-specific implementation of [CryptoManager].
 * Provides cryptographic operations using the Ed25519 algorithm with Libsodium.
 */
@JsExport
actual object CryptoManager {

    @JsExport.Ignore
    internal actual suspend fun initialize() {
        Sodium.ready.await()
        SdkLogger.d("Successfully initialized Libsodium")
    }

    /**
     * @see [CryptoManager.generateRawKeyPair]
     */
    internal actual fun generateRawKeyPair(): Crypto.KeyPair {
        val keyPair = Sodium.crypto_sign_keypair()
        return Crypto.KeyPair(
            private = (keyPair.privateKey as Uint8Array).toByteArray(),
            public = (keyPair.publicKey as Uint8Array).toByteArray()
        )
    }

    fun generateKeyPair(): Crypto.KeyPair = generateRawKeyPair()

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
        CRYPTO_KIT_PRIVATE_KEY_SIZE -> toSecretKeyBytes()
        SODIUM_PRIVATE_KEY_SIZE -> this
        BOUNCY_CASTLE_PRIVATE_KEY_SIZE ->
            sliceArray(PRIVATE_KEY_ASN1_HEADER.size until JAVA_PKCS8_PRIVATE_KEY_SIZE).toSecretKeyBytes()
        JAVA_PKCS8_PRIVATE_KEY_SIZE -> sliceArray(size - RAW_KEY_SIZE until size).toSecretKeyBytes()
        else -> throw SdkException("Unknown private key size: $size")
    }

    private fun ByteArray.toSecretKeyBytes(): ByteArray {
        val keyPair = Sodium.crypto_sign_seed_keypair(toUint8Array())
        return (keyPair.privateKey as Uint8Array).toByteArray()
    }

    /**
     * @see [CryptoManager.signWithPrivateKey]
     */
    @JsExport.Ignore
    internal actual fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
        Sodium.crypto_sign_detached(
            message = toByteArray().toUint8Array(),
            privateKey = privateKey.toPlatformPrivateKey().toUint8Array()
        ).toByteArray()
    } catch (exception: Exception) {
        throw SdkException("Failed to sign with private key", exception)
    }

    /**
     * @see [CryptoManager.verifySignature]
     */
    @JsExport.Ignore
    internal actual fun ByteArray.verifySignature(publicKey: ByteArray, message: String): Boolean = try {
        return Sodium.crypto_sign_verify_detached(
            signature = toUint8Array(),
            message = message.toByteArray().toUint8Array(),
            publicKey = publicKey.toPlatformPublicKey().toUint8Array()
        )
    } catch (exception: Exception) {
        SdkLogger.e(exception) { "Failed to verify signature" }
        false
    }
}

@JsExport
fun crypto(): CryptoManager = CryptoManager