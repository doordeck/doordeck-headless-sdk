package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.model.data.FailedResultData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlinx.coroutines.CompletableDeferred
import kotlin.test.BeforeTest
import kotlin.test.fail

internal val TestCallback: CStringCallback = staticCFunction(::testCallback)

private var pendingCallback: CompletableDeferred<String>? = null

internal fun testCallback(data: CPointer<ByteVar>): CPointer<ByteVar> {
    pendingCallback?.complete(data.toKString())
    return data
}

/**
 * Runs [apiCall] and suspends until [TestCallback] delivers its result.
 *
 * It suspends rather than blocking the thread, so that the timeouts of the enclosing test (`withTimeout`, `runTest`)
 * can cancel the wait when a callback never arrives. A blocking wait would hide the hang from them and the test
 * would only end with `runTest`'s uncancellable `UncompletedCoroutinesError`.
 */
internal suspend inline fun <reified T> callbackApiCall(
    crossinline apiCall: () -> Unit
): T {
    val deferred = CompletableDeferred<String>().also { pendingCallback = it }
    try {
        apiCall()
        return deferred.await().fromJson<T>()
    } finally {
        pendingCallback = null
    }
}

open class CallbackTest : IntegrationTest() {
    @BeforeTest fun resetCallback() { pendingCallback = null }
}

internal inline fun <reified T> ResultData<T>.unwrap(): T {
    failure?.let { fail("API error [${it.exceptionType}]: ${it.exceptionMessage}") }
    val success = checkNotNull(success) { "Both success and failure were null" }
    return success.result ?: when {
        T::class == Unit::class -> {
            @Suppress("UNCHECKED_CAST")
            Unit as T
        }
        else -> fail("Expected ${T::class.simpleName} but success.result was null")
    }
}

internal fun <T> ResultData<T>.unwrapFailure(): FailedResultData {
    success?.let {
        fail("Expected failure but got success")
    }
    return checkNotNull(failure) {
        "Expected failure but both success and failure were null"
    }
}
