package com.doordeck.multiplatform.sdk

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

private var capturedCallback: String? = null

internal fun testCallback(data: CPointer<ByteVar>): CPointer<ByteVar> {
    val received = data.toKString()
    capturedCallback = received
    return data
}

open class CallbackTest : MockTest() {
    @BeforeTest
    fun resetCallback() = runTest {
        capturedCallback = null
    }

    fun callbackTest(apiCall: suspend () -> Unit, expectedResponse: String = UNIT_RESULT_DATA) {
        runBlocking {
            // When
            apiCall()
            delay(5.milliseconds)

            // Then
            assertEquals(expectedResponse, capturedCallback)
        }
    }
}