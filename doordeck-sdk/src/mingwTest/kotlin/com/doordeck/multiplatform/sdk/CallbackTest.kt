package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.test.BeforeTest
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private var capturedCallback: String = ""

internal fun testCallback(data: CPointer<ByteVar>): CPointer<ByteVar> {
    val received = data.toKString()
    capturedCallback = received
    return data
}

internal inline fun <reified T> callbackApiCall(
    noinline apiCall: suspend () -> Unit
): T = runBlocking {
    apiCall()
    withTimeout(60.seconds) {
        while (capturedCallback.isEmpty()) delay(10.milliseconds)
    }
    capturedCallback.fromJson<T>().also { capturedCallback = "" }
}

open class CallbackTest : IntegrationTest() {
    @BeforeTest fun resetCallback() { capturedCallback = "" }
}

open class BasicCallbackTest {
    @BeforeTest fun resetCallback() { capturedCallback = "" }
}