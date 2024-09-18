package com.doordeck.multiplatform.sdk.util

import io.ktor.client.*

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    // Certificate pinner is not supported on the WinHttp engine
}