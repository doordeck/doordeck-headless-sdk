package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import io.ktor.client.engine.darwin.certificates.CertificatePinner
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toNSDate
import kotlinx.datetime.toNSDateComponents
import kotlinx.datetime.toNSTimeZone
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
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
import platform.Foundation.serverTrust
import platform.Foundation.timeIntervalSince1970
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

internal fun String.toNSURLComponents(): NSURLComponents = NSURLComponents(this)

internal fun NSURLComponents.toUrlString(): String = string!!


internal fun String.toNsTimeZone(): NSTimeZone = TimeZone.of(this).toNSTimeZone()

private val TIME_FORMAT = NSDateFormatter().apply {
    dateFormat = "hh:mm"
}

private val DATE_FORMAT = NSDateFormatter().apply {
    dateFormat = "yyyy-MM-dd"
}

internal fun String.toNsDateComponents(): NSDateComponents {
    return NSCalendar.currentCalendar.components(
        unitFlags = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        fromDate = TIME_FORMAT.dateFromString(this)!!
    )
}

internal fun String.toNsLocalDateComponents(): NSDateComponents {
    return NSCalendar.currentCalendar.components(
        unitFlags = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        fromDate = DATE_FORMAT.dateFromString(this)!!
    )
}

internal fun NSDateComponents.toLocalTimeString(): String = TIME_FORMAT.stringFromDate(
    NSCalendar.currentCalendar.dateFromComponents(this)!!
)

internal fun NSDateComponents.toLocalDateString(): String = DATE_FORMAT.stringFromDate(
    NSCalendar.currentCalendar.dateFromComponents(this)!!
)

internal fun NSDate.toEpochSeconds(): Long = timeIntervalSince1970.toLong()

internal fun NSTimeInterval.toWholeSeconds(): Int = toInt()

internal fun Double.toNsDate(): NSDate = toString().toNsDate()

internal fun String.toNsDate(): NSDate {
    val split = split(".")
    return Instant.fromEpochSeconds(
        epochSeconds = split.first().toLong(),
        nanosecondAdjustment = split.lastOrNull()?.toLong() ?: 0
    ).toNSDate()
}
