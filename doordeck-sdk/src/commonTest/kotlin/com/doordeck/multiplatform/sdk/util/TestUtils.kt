package com.doordeck.multiplatform.sdk.util

import kotlin.test.fail

inline fun assertDoesNotThrow(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        fail("Expected no exception, but got: ${e::class.simpleName}: ${e.message}")
    }
}