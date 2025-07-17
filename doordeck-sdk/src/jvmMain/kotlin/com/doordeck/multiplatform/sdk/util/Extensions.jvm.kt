package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.Constants.CERTIFICATE_PINNER_DOMAIN_PATTERN
import com.doordeck.multiplatform.sdk.Constants.TRUSTED_CERTIFICATES
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import java.util.UUID
import java.util.concurrent.CompletableFuture

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

internal fun String.toUUID(): UUID = UUID.fromString(this)

/**
 * Creates a `CompletableFuture` from a suspendable function.
 *
 * This function executes the given suspend function within a coroutine context
 * and returns a `CompletableFuture` that completes once the suspend function
 * finishes. The coroutine is launched in the `IO` dispatcher.
 *
 * @param T The type of the result produced by the suspend function.
 * @param block The suspend function that will be executed asynchronously.
 *
 * @return A `CompletableFuture` that completes with the result of the suspend function.
 */
internal inline fun <reified T>completableFuture(crossinline block: suspend () -> T): CompletableFuture<T> {
    return GlobalScope.future(Dispatchers.IO) {
        block()
    }
}