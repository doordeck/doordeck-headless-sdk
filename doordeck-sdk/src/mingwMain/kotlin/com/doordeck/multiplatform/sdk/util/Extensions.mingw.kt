package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import io.ktor.client.HttpClientConfig

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    // Certificate pinner is not supported on the WinHttp engine
}

/**
 * Utility extension to expose the `ApiEnvironment` enum name
 */
fun ApiEnvironment.getApiEnvironmentName(): String {
    return name
}