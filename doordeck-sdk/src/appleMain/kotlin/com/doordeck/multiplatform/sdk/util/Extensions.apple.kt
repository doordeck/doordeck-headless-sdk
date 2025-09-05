package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import io.ktor.client.engine.darwin.certificates.CertificatePinner
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
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


private val NS_TIME_FORMAT = NSDateFormatter().apply {
    dateFormat = "HH:mm"
}

private val NS_DATE_FORMAT = NSDateFormatter().apply {
    dateFormat = "yyyy-MM-dd"
}

internal fun String.toNsTimeComponents(): NSDateComponents = NSCalendar.currentCalendar.components(
    unitFlags = NSCalendarUnitHour or NSCalendarUnitMinute,
    fromDate = NS_TIME_FORMAT.dateFromString(this)!!
)

internal fun String.toNsDateComponents(): NSDateComponents = NSCalendar.currentCalendar.components(
    unitFlags = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
    fromDate = NS_DATE_FORMAT.dateFromString(this)!!
)

internal fun NSDateComponents.toTimeString(): String = NS_TIME_FORMAT.stringFromDate(
    NSCalendar.currentCalendar.dateFromComponents(this)!!
)

internal fun NSDateComponents.toDateString(): String = NS_DATE_FORMAT.stringFromDate(
    NSCalendar.currentCalendar.dateFromComponents(this)!!
)

internal fun NSDate.toEpochSeconds(): Long = timeIntervalSince1970.toLong()

internal fun NSTimeInterval.toWholeSeconds(): Int = toInt()

internal fun Double.toNsDate(): NSDate = toString().toNsDate()

internal fun String.toNsDate(): NSDate = NSDate.dateWithTimeIntervalSince1970(toDouble())
