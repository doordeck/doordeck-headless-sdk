package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.data.SuccessResultData
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.assertNotNull
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
        runBlocking {
            apiCall()
            delay(1.seconds)
        }
        return capturedCallback.fromJson<T>()
    }
}