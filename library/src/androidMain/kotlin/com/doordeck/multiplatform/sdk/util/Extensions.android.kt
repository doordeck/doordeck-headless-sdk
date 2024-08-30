package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    engine {
        if (this is OkHttpConfig) {
            val certificatePinner = CertificatePinner.Builder()
                .add(CERTIFICATE_PINNER_DOMAIN_PATTERN, *TRUSTED_CERTIFICATES.toTypedArray())
                .build()
            preconfigured = OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build()
        }
    }
}