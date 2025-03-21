package com.doordeck.multiplatform.sdk

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest

var capturedCallback: String? = null

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
}