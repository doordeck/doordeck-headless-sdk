package com.doordeck.multiplatform.sdk.util

import at.asitplus.signum.indispensable.asn1.Asn1Element
import at.asitplus.signum.indispensable.asn1.Asn1Time
import at.asitplus.signum.indispensable.asn1.encoding.parse
import com.doordeck.multiplatform.sdk.crypto.MIN_CERTIFICATE_LIFETIME_DAYS
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.datetime.Clock

internal fun String.isCertificateAboutToExpire(): Boolean {
    return try {
        // Retrieve the 'Not After' element
        val notAfterElement = Asn1Element.parse(decodeBase64ToByteArray())
            .asSequence().children.elementAtOrNull(0)?.asSequence() // Tabs
            ?.children?.elementAtOrNull(4)?.asSequence() // Validity
            ?.children?.elementAtOrNull(1) // Not after
        if (notAfterElement == null) {
            return true
        }
        val notAfterInstant = Asn1Time.decodeFromDer(notAfterElement.derEncoded)
            .instant
        return Clock.System.now() >= notAfterInstant - MIN_CERTIFICATE_LIFETIME_DAYS
    } catch (exception: Throwable) {
        true
    }
}