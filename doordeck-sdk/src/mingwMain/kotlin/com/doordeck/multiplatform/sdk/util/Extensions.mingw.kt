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
@CName("getApiEnvironmentName")
fun ApiEnvironment.getApiEnvironmentName(): String {
    return name
}

/**
 * Utility extension to retrieve the ``ApiEnvironment`` by its name
 */
@CName("getApiEnvironmentByName")
fun ApiEnvironment.getApiEnvironmentByName(name: String): ApiEnvironment {
    return ApiEnvironment.valueOf(name)
}

/**
 * Executes a block of code and captures its result. Wraps either the result of the block or any exception thrown
 * during its execution into a serialized JSON `ResultData` string.
 *
 * @param T The type of the result produced by the block.
 * @param block A lambda function that represents the code to execute.
 * @return A JSON string representing the result. If the block executes successfully,
 *          the result is wrapped in `SuccessResultData`.
 *          If an exception occurs, it is wrapped in `FailedResultData` along with an error message.
 */
internal inline fun <reified T>resultData(crossinline block: () -> T): String {
    return try {
        val result = block()
        val success = if (result != Unit) result else null
        ResultData(SuccessResultData(success))
    } catch (exception: Throwable) {
        val errorMessage = exception.message ?: exception.cause?.message ?: "Unknown error occurred"
        ResultData(failure = FailedResultData(exception.toString(), errorMessage))
    }.toJson()
}