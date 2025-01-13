package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.api.model.FailedResultData
import com.doordeck.multiplatform.sdk.api.model.ResultData
import com.doordeck.multiplatform.sdk.api.model.SuccessResultData
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

internal inline fun <reified T>resultData(crossinline block: () -> T): String {
    return try {
        val result = block()
        val success = if (result != Unit) result else null
        ResultData(SuccessResultData(success))
    } catch (exception: Exception) {
        val errorMessage = exception.message ?: exception.cause?.message ?: "Unknown error occurred"
        ResultData(failure = FailedResultData(exception.toString(), errorMessage))
    }.toJson()
}