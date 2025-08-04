package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import kotlin.test.BeforeTest
import kotlin.time.Duration.Companion.seconds

private var capturedCallback: String = ""

internal fun testCallback(data: CPointer<ByteVar>): CPointer<ByteVar> {
    val received = data.toKString()
    capturedCallback = received
    return data
}

open class CallbackTest : IntegrationTest() {
    @BeforeTest
    fun resetCallback() = runTest {
        capturedCallback = ""
    }

    internal inline fun <reified T> callbackApiCall(noinline apiCall: suspend () -> Unit): T {
        return runBlocking {
            apiCall()
            withTimeout(60.seconds) {
                while (capturedCallback.isEmpty()) {
                    delay(1.seconds)
                }
            }
            return@runBlocking capturedCallback.fromJson<T>().also {
                capturedCallback = ""
            }
        }
    }
}