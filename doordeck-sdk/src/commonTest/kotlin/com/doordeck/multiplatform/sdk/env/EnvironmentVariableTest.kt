package com.doordeck.multiplatform.sdk.env

import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.util.assertDoesNotThrow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class EnvironmentVariableTest {

    @Test
    fun shouldRetrieveEnvironmentVariable() = runTest {
        // Given
        val testEnvVar = "9f8e96ae-bed8-43a4-ac5e-2f55dc6a85cb"

        // When
        val result = getEnvironmentVariable("TEST_ENV_VAR")

        // Then
        assertNotNull(result)
        assertEquals(testEnvVar, result)
    }

    @Test
    fun shouldNotThrowOnNonExistingEnvironmentVariable() = runTest {
        // Given
        val envVar = randomString()

        // When
        val restored = assertDoesNotThrow {
            getEnvironmentVariable(envVar)
        }

        // Then
        assertNull(restored)
    }
}