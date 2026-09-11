package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.ResultCallback
import com.doordeck.multiplatform.sdk.model.data.FailedResultData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.data.SuccessResultData
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
 * Runs [block] and reports anything it throws through the callback. Kotlin/Native terminates the
 * process when an exception reaches a C entry point, so nothing may propagate out of the dispatcher:
 * a caller that gets a request wrong must receive a failure, not lose its process.
 */
internal inline fun ResultCallback.guard(id: Long, block: () -> Unit) {
    try {
        block()
    } catch (exception: Throwable) {
        reply<Unit>(id) { throw exception }
    }
}

internal inline fun <reified T> ResultCallback.reply(id: Long, block: () -> T) =
    emit(id, this, runCatching { block() })

internal inline fun <reified T> ResultCallback.replyAsync(id: Long, crossinline block: suspend () -> T) {
    GlobalScope.launch(Dispatchers.Default) { emit(id, this@replyAsync, runCatching { block() }) }
}

internal inline fun <reified T> emit(id: Long, cb: ResultCallback?, outcome: Result<T>) {
    val json = outcome.fold(
        onSuccess = { ResultData(SuccessResultData(it.takeIf { v -> v != Unit })) },
        onFailure = {
            // Same fallback chain handleCallback used: a wrapped exception often carries its
            // detail on the cause rather than on itself.
            ResultData(failure = FailedResultData(it.toString(), it.message ?: it.cause?.message ?: "Unknown error occurred"))
        }
    ).toJson()
    cb?.let { memScoped { it.invoke(id, json.cstr.ptr) } }
}