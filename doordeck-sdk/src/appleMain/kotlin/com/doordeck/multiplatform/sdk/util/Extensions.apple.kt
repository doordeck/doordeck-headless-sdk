package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import io.ktor.client.engine.darwin.certificates.CertificatePinner
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSURLAuthenticationMethodServerTrust
import platform.Foundation.NSURLCredential
import platform.Foundation.NSURLSessionAuthChallengePerformDefaultHandling
import platform.Foundation.NSURLSessionAuthChallengeUseCredential
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