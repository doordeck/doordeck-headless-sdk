package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import io.ktor.client.engine.darwin.certificates.CertificatePinner
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarIdentifierGregorian
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSTimeInterval
import platform.Foundation.NSTimeZone
import platform.Foundation.NSURLAuthenticationMethodServerTrust
import platform.Foundation.NSURLComponents
import platform.Foundation.NSURLCredential
import platform.Foundation.NSURLSessionAuthChallengePerformDefaultHandling
import platform.Foundation.NSURLSessionAuthChallengeUseCredential
import platform.Foundation.NSUUID
import platform.Foundation.credentialForTrust
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.serverTrust
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.timeZoneWithAbbreviation
import platform.Foundation.timeZoneWithName
import kotlin.time.Instant

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    engine {
        if (this is DarwinClientEngineConfig) {
            if (!isRunningOnSimulator()) {
                val certificatePinner = CertificatePinner.Builder()
                    .add(CERTIFICATE_PINNER_DOMAIN_PATTERN, *TRUSTED_CERTIFICATES.toTypedArray())
                handleChallenge(certificatePinner.build())
            } else {
                handleChallenge { session, task, challenge, completionHandler ->
                    if (challenge.protectionSpace.authenticationMethod == NSURLAuthenticationMethodServerTrust) {
                        val serverTrust = challenge.protectionSpace.serverTrust!!
                        completionHandler(
                            NSURLSessionAuthChallengeUseCredential,
                            NSURLCredential.credentialForTrust(serverTrust)
                        )
                    } else {
                        completionHandler(
                            NSURLSessionAuthChallengePerformDefaultHandling,
                            null
                        )
                    }
                }
            }
        }
    }
}

private fun isRunningOnSimulator(): Boolean =
    NSProcessInfo.processInfo.environment["SIMULATOR_DEVICE_NAME"] != null

internal fun String.toNsUuid(): NSUUID = NSUUID(this)

internal fun String.toNsUrlComponents(): NSURLComponents = NSURLComponents(this)

internal fun NSURLComponents.toUrlString(): String = string!!

internal fun String.toNsTimeZone(): NSTimeZone = (NSTimeZone.timeZoneWithName(this)
    ?: NSTimeZone.timeZoneWithAbbreviation(this))!!


// These helpers convert wall-clock times ("HH:mm") and calendar dates ("yyyy-MM-dd") that carry no
// absolute-instant meaning. Both the formatters and the calendar are pinned to the POSIX locale, the
// Gregorian calendar and UTC so the result is independent of the device's regional settings. Relying on
// NSCalendar.currentCalendar / the default locale would corrupt the value on devices configured with a
// non-Gregorian calendar (e.g. Buddhist -> year 2567) or a 12/24-hour override.
private val POSIX_LOCALE = NSLocale(localeIdentifier = "en_US_POSIX")

private val UTC_TIME_ZONE = NSTimeZone.timeZoneWithName("UTC")!!

private val GREGORIAN_CALENDAR = NSCalendar(calendarIdentifier = NSCalendarIdentifierGregorian).apply {
    locale = POSIX_LOCALE
    timeZone = UTC_TIME_ZONE
}

private val NS_TIME_FORMAT = NSDateFormatter().apply {
    dateFormat = "HH:mm"
    locale = POSIX_LOCALE
    calendar = GREGORIAN_CALENDAR
    timeZone = UTC_TIME_ZONE
}

private val NS_DATE_FORMAT = NSDateFormatter().apply {
    dateFormat = "yyyy-MM-dd"
    locale = POSIX_LOCALE
    calendar = GREGORIAN_CALENDAR
    timeZone = UTC_TIME_ZONE
}

internal fun String.toNsTimeComponents(): NSDateComponents = GREGORIAN_CALENDAR.components(
    unitFlags = NSCalendarUnitHour or NSCalendarUnitMinute,
    fromDate = NS_TIME_FORMAT.dateFromString(this)!!
)

internal fun String.toNsDateComponents(): NSDateComponents = GREGORIAN_CALENDAR.components(
    unitFlags = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
    fromDate = NS_DATE_FORMAT.dateFromString(this)!!
)

internal fun NSDateComponents.toTimeString(): String = NS_TIME_FORMAT.stringFromDate(
    GREGORIAN_CALENDAR.dateFromComponents(this)!!
)

internal fun NSDateComponents.toDateString(): String = NS_DATE_FORMAT.stringFromDate(
    GREGORIAN_CALENDAR.dateFromComponents(this)!!
)

internal fun NSDate.toEpochSeconds(): Long = timeIntervalSince1970.toLong()

internal fun NSTimeInterval.toWholeSeconds(): Int = toInt()

internal fun Double.toNsDate(): NSDate = toString().toNsDate()

internal fun String.toNsDate(): NSDate = NSDate.dateWithTimeIntervalSince1970(toDouble())

internal fun Long.epochSecondToNsDate(): NSDate = NSDate.dateWithTimeIntervalSince1970(toDouble())

internal fun String.isoToNsDate(): NSDate = NSDate.dateWithTimeIntervalSince1970(Instant.parse(this).epochSeconds.toDouble())