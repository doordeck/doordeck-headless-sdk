package com.doordeck.multiplatform.sdk

import kotlin.test.fail

fun assertDoesNotThrow(block: () -> Unit) {
    try {
        block()
    } catch (exception: Throwable) {
        fail("Expected no exception, but got: $exception")
    }
}