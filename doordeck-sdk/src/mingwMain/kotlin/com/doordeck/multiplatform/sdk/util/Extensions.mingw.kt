package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.CStringCallback
import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.model.data.FailedResultData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.data.SuccessResultData
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import io.ktor.client.HttpClientConfig
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal actual fun HttpClientConfig<*>.installCertificatePinner() {
    // Certificate pinner is not supported on the WinHttp engine
}

/**
 * Utility extension for easily building an [SdkConfig] instance externally.
 */
@CName("buildSdkConfig")
fun buildSdkConfig(
    apiEnvironment: String,
    cloudAuthToken: String? = null,
    cloudRefreshToken: String? = null,
    fusionHost: String? = null,
    secureStorage: SecureStorage? = null,
    debugLogging: String? = null
): SdkConfig {
    return SdkConfig.Builder()
        .setApiEnvironment(apiEnvironment)
        .setCloudAuthToken(cloudAuthToken)
        .setCloudRefreshToken(cloudRefreshToken)
        .setFusionHost(fusionHost)
        .setSecureStorageOverride(secureStorage)
        .setDebugLogging(debugLogging)
        .build()
}

/**
 * Executes a suspend [block] asynchronously and passes its result to a native [handleCallback]. Wraps either the result
 * of the block or any exception thrown during its execution into a serialized JSON `ResultData` string.
 *
 * @param T The type of the result produced by the [block] function.
 * @param block A suspend function that returns a result of type [T]. Its successful output is processed as described.
 * @param handleCallback A pointer to a C function that accepts a C-style string pointer ([CPointer<ByteVar>]) and returns a C-style string pointer.
 */
internal inline fun <reified T> CStringCallback.handleCallback(
    crossinline block: suspend () -> T
) {
    GlobalScope.launch(Dispatchers.Default) {
        val result: String = try {
            val result = block()
            val success = if (result != Unit) result else null
            ResultData(SuccessResultData(success))
        } catch (exception: Throwable) {
            val errorMessage = exception.message ?: exception.cause?.message ?: "Unknown error occurred"
            ResultData(failure = FailedResultData(exception.toString(), errorMessage))
        }.toJson()

        memScoped {
            val cString = result.cstr.ptr
            invoke(cString)
        }
    }
}