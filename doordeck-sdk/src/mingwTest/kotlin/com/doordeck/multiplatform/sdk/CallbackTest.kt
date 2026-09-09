package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.model.data.FailedResultData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.test.BeforeTest
import kotlin.test.fail
import kotlin.time.Duration.Companion.seconds

internal val TestCallback: ResultCallback = staticCFunction(::testCallback)

private var pendingCallback: CompletableDeferred<String>? = null

internal fun testCallback(requestId: Long, data: CPointer<ByteVar>) {
    pendingCallback?.complete(data.toKString())
}

internal inline fun <reified T> callbackApiCall(
    crossinline apiCall: () -> Unit
): T = runBlocking {
    val deferred = CompletableDeferred<String>().also { pendingCallback = it }
    try {
        apiCall()
        withTimeout(60.seconds) { deferred.await() }.fromJson<T>()
    } finally {
        pendingCallback = null
    }
}

open class CallbackTest : IntegrationTest() {
    @BeforeTest fun resetCallback() { pendingCallback = null }
}

open class BasicCallbackTest {
    @BeforeTest fun resetCallback() { pendingCallback = null }
}

internal inline fun <reified T> ResultData<T>.unwrap(): T {
    failure?.let { fail("API error [${it.exceptionType}]: ${it.exceptionMessage}") }
    val success = checkNotNull(success) { "Both success and failure were null" }
    val result = success.result
    @Suppress("UNCHECKED_CAST")
    return when {
        result != null -> result
        T::class == Unit::class -> Unit as T
        // Ask for a nullable type and a null result is a value; ask for a non-null one and it is a
        // failure. That keeps every existing call site strict without needing a second helper.
        null is T -> null as T
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