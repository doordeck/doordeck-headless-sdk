package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import io.ktor.client.engine.darwin.certificates.CertificatePinner
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSTimeZone
import platform.Foundation.NSURL
import platform.Foundation.NSURLAuthenticationMethodServerTrust
import platform.Foundation.NSURLCredential
import platform.Foundation.NSURLSessionAuthChallengePerformDefaultHandling
import platform.Foundation.NSURLSessionAuthChallengeUseCredential
import platform.Foundation.NSUUID
import platform.Foundation.create
import platform.Foundation.credentialForTrust
import platform.Foundation.serverTrust

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

internal fun String.toNsUrl(): NSURL = NSURL.URLWithString(this)!!

internal fun NSURL.toUrlString(): String = absoluteString!!

internal fun String.toNsTimeZone(): NSTimeZone = NSTimeZone.create(this)!!

private val TIME_FORMAT = NSDateFormatter().apply {
    dateFormat = "hh:mm"
}

internal fun NSDate.totoLocalTimeString(): String = TIME_FORMAT.stringFromDate(this)

internal fun String.toInstant(): NSDate = NSDate(toDouble())