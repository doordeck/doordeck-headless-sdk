package com.doordeck.multiplatform.sdk.util

import at.asitplus.signum.indispensable.asn1.Asn1Element
import at.asitplus.signum.indispensable.asn1.Asn1Time
import at.asitplus.signum.indispensable.asn1.encoding.parse
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.crypto.MIN_CERTIFICATE_LIFETIME_DAYS
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlin.jvm.JvmSynthetic

/**
 * Checks whether the certificate represented by this Base64-encoded string is invalid or expired
 * (we consider it expired if it will expire within the next [MIN_CERTIFICATE_LIFETIME_DAYS] days).
 *
 * The function parses the certificate's ASN.1 structure to find the
 * 'Not After' validity date and compares it against the current time minus the minimum required certificate lifetime.
 *
 * @receiver A Base64-encoded string containing certificate data
 * @return
 *  - `true` If the certificate's 'Not After' date is within [MIN_CERTIFICATE_LIFETIME_DAYS] days of the current time
 *  - `true` If the certificate's expiration date cannot be parsed or retrieved
 *  - `true` If any exception occurs during certificate parsing
 *  - `false` If the certificate has more than [MIN_CERTIFICATE_LIFETIME_DAYS] days remaining before expiration
 */
@JvmSynthetic
internal fun String.isCertificateInvalidOrExpired(): Boolean {
    return try {
        // Retrieve the 'Not After' element
        val notAfterElement = Asn1Element.parse(decodeBase64ToByteArray())
            .asSequence().children.elementAtOrNull(0)?.asSequence() // Tabs
            ?.children?.elementAtOrNull(4)?.asSequence() // Validity
            ?.children?.elementAtOrNull(1) // Not after
        // If no element is found, we will treat the certificate as expired
        if (notAfterElement == null) {
            SdkLogger.d { "Unable to retrieve the expiration date from the certificate" }
            return true
        }
        // Decode the time value
        val notAfterInstant = Asn1Time.decodeFromDer(notAfterElement.derEncoded)
            .instant
        SdkLogger.d { "Certificate expiration date is $notAfterInstant" }
        return SystemClock.now() >= notAfterInstant - MIN_CERTIFICATE_LIFETIME_DAYS
    } catch (exception: Throwable) {
        SdkLogger.e(exception) { "Failed to parse the certificate" }
        true
    }
}
/**
 * The DER encoding of the userId attribute (OID 0.9.2342.19200300.100.1.1), the one field the
 * backend puts in a user's master certificate subject.
 */
private val USER_ID_ATTRIBUTE = byteArrayOf(
    0x06, 0x0A, 0x09, 0x92.toByte(), 0x26, 0x89.toByte(), 0x93.toByte(), 0xF2.toByte(), 0x2C, 0x64, 0x01, 0x01
)

/**
 * Reads the id of the user this certificate was issued to, or null when it does not say.
 *
 * An ephemeral certificate is issued by the user's master certificate, whose subject is that
 * user's id and nothing else, so the issuer of the first certificate in a chain is who the chain
 * belongs to. The issuer is the fourth field of the TBSCertificate — version, serial number,
 * signature, issuer — and it is a sequence of sets of (attribute, value) pairs.
 *
 * @receiver A Base64-encoded string containing certificate data
 */
@JvmSynthetic
internal fun String.getCertificateUserId(): String? {
    return try {
        val issuer = Asn1Element.parse(decodeBase64ToByteArray())
            .asSequence().children.elementAtOrNull(0)?.asSequence() // Tabs
            ?.children?.elementAtOrNull(3)?.asSequence() // Issuer
        if (issuer == null) {
            SdkLogger.d { "Unable to retrieve the issuer from the certificate" }
            return null
        }
        issuer.children.firstNotNullOfOrNull { relativeName ->
            relativeName.asSet().children.firstNotNullOfOrNull { attribute ->
                val pair = attribute.asSequence().children
                val value = pair.elementAtOrNull(1)
                if (value != null && pair.elementAtOrNull(0)?.derEncoded.contentEquals(USER_ID_ATTRIBUTE)) {
                    value.asPrimitive().content.decodeToString()
                } else null
            }
        }
    } catch (exception: Throwable) {
        SdkLogger.e(exception) { "Failed to parse the certificate" }
        null
    }
}
