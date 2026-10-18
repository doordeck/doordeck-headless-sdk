package com.doordeck.multiplatform.sdk.util

import kotlin.test.fail

inline fun <T> assertDoesNotThrow(block: () -> T): T {
    return try {
        block()
    } catch (e: Throwable) {
        fail("Expected no exception, but got: ${e::class.simpleName}: ${e.message}")
    }
}