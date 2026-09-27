package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.util.assertDoesNotThrow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SecureStorageTest {

    @Test
    fun shouldInitializeDefaultSecureStorage() = runTest {
        assertDoesNotThrow {
            createSecureStorage()
        }
    }
}