package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomNullable
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.unwrap
import com.doordeck.multiplatform.sdk.unwrapFailure
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MingwExtensionsTest : CallbackTest() {

    @Test
    fun shouldMapSdkConfig() = runTest {
        // Given
        val apiEnvironment = "DEV"
        val cloudAuthToken = randomNullable { "cloudAuthToken" }
        val cloudRefreshToken = randomNullable { "cloudRefreshToken" }
        val fusionHost = randomNullable { "fusionHost" }
        val secureStorage = randomNullable { DefaultSecureStorage(MemorySettings()) }
        val debugLogging = randomNullable { randomBoolean().toString() }

        // When
        val result = buildSdkConfig(
            apiEnvironment = apiEnvironment,
            cloudAuthToken = cloudAuthToken,
            cloudRefreshToken = cloudRefreshToken,
            fusionHost = fusionHost,
            secureStorage = secureStorage,
            debugLogging = debugLogging
        )

        // Then
        assertEquals(apiEnvironment, result.apiEnvironment)
        assertEquals(cloudAuthToken, result.cloudAuthToken)
        assertEquals(cloudRefreshToken, result.cloudRefreshToken)
        assertEquals(fusionHost, result.fusionHost)
        assertEquals(debugLogging, result.debugLogging)
    }

    @Test
    fun shouldHandleCallbackSuccess() {
        // Given
        val expectedResult = "success"

        // When
        val result = callbackApiCall<ResultData<String>> {
            TestCallback.handleCallback {
                expectedResult
            }
        }

        // Then
        assertEquals(expectedResult, result.unwrap())
    }

    @Test
    fun shouldHandleCallbackSuccessUnit() {
        // Given
        val unit = Unit

        val result = callbackApiCall<ResultData<Unit>> {
            TestCallback.handleCallback {
                unit
            }
        }

        // Then
        assertEquals(unit, result.unwrap())
    }

    @Test
    fun shouldHandleCallbackFailure() {
        // Given
        val errorMessage = "test error"

        // When
        val result = callbackApiCall<ResultData<String>> {
            TestCallback.handleCallback<String> {
                throw Exception(errorMessage)
            }
        }

        // Then
        val failure = result.unwrapFailure()
        assertEquals(errorMessage, failure.exceptionMessage)
    }
}