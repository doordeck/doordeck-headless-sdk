package com.doordeck.multiplatform.sdk.util

import at.asitplus.signum.indispensable.asn1.Asn1Element
import at.asitplus.signum.indispensable.asn1.Asn1Time
import at.asitplus.signum.indispensable.asn1.encoding.parse
import com.doordeck.multiplatform.sdk.crypto.MIN_CERTIFICATE_LIFETIME_DAYS
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.datetime.Clock

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
        return Clock.System.now() >= notAfterInstant - MIN_CERTIFICATE_LIFETIME_DAYS
    } catch (exception: Throwable) {
        SdkLogger.e(exception) { "Failed to parse the certificate" }
        true
    }
}