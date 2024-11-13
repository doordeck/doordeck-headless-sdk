package com.doordeck.multiplatform.sdk.util

import io.ktor.client.HttpClientConfig

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    // Certificate pinner is not supported on the JS engine
}