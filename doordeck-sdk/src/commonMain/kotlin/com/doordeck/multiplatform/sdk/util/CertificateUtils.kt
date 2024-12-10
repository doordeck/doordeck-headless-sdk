package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Duration.Companion.days

internal fun String.isCertificateAboutToExpire(): Boolean = try {
    val notAfter = extractNotAfter(decodeBase64ToByteArray())
    val notAfterInstant = parseNotAfter(notAfter.decodeToString())
    Clock.System.now() >= notAfterInstant - 30.days
} catch (exception: Exception) {
    true
}

private fun extractNotAfter(certificate: ByteArray): ByteArray {
    // ASN.1 tag for UTC time (0x17) is used to identify time-related fields in X.509 certificates.
    val notBeforeTag = byteArrayOf(0x17) // 0x17 represents the ASN.1 tag for UTC time.

    // Find the index where the "Not Before" field starts in the certificate.
    // The certificate is a sequence of bytes, and we're looking for a match to the "Not Before" tag.
    val index = certificate.indices.first {
        // Check if the tag at the current index matches the "Not Before" tag.
        it <= certificate.size - notBeforeTag.size &&
                certificate.copyOfRange(it, it + notBeforeTag.size).contentEquals(notBeforeTag)
    }

    // The byte after the "Not Before" tag contains the length of the "Not Before" field in the certificate.
    // The length byte is located at index + 1.
    val notBeforeLength = certificate[index + 1].toInt()

    // The "Not Before" field ends at index + 2 + notBeforeLength.
    val notBeforeEndIndex = index + 2 + notBeforeLength

    // Now, we are looking for the "Not After" field which comes immediately after the "Not Before" field.
    // We search for the "Not After" tag, which is the same as the "Not Before" tag (0x17) for UTC time.
    val notAfterTag = byteArrayOf(0x17) // Same ASN.1 tag (0x17) for UTC time, indicating "Not After".

    // Search within the remaining portion of the certificate, starting from the end of the "Not Before" field.
    val notAfterIndex = (notBeforeEndIndex until certificate.size).first {
        // Look for the "Not After" tag in the current range of the certificate bytes.
        certificate.slice(it until it + notAfterTag.size).toByteArray().contentEquals(notAfterTag)
    }

    // The byte after the "Not After" tag contains the length of the "Not After" field.
    // The length byte is located at notAfterIndex + 1.
    val notAfterLength = certificate[notAfterIndex + 1].toInt()

    // Extract and return the "Not After" field using the length information.
    // The "Not After" field starts at notAfterIndex + 2 and ends at notAfterIndex + 2 + notAfterLength.
    return certificate.copyOfRange(notAfterIndex + 2, notAfterIndex + 2 + notAfterLength)
}

private fun parseNotAfter(timeString: String): Instant {
    val year = 2000 + timeString.substring(0, 2).toInt()
    val month = timeString.substring(2, 4).toInt()
    val day = timeString.substring(4, 6).toInt()
    val hour = timeString.substring(6, 8).toInt()
    val minute = timeString.substring(8, 10).toInt()
    val second = timeString.substring(10, 12).toInt()
    val localDateTime = LocalDateTime(year, month, day, hour, minute, second)
    return localDateTime.toInstant(TimeZone.UTC)
}