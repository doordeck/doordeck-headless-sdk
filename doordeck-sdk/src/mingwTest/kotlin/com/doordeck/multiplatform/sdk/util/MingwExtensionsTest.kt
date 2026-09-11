package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.unwrap
import com.doordeck.multiplatform.sdk.unwrapFailure
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class MingwExtensionsTest : CallbackTest() {

    @Test
    fun shouldHandleCallbackSuccess() {
        // Given
        val expectedResult = "success"

        // When
        val result = callbackApiCall<ResultData<String>> {
            TestCallback.replyAsync(0) {
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
            TestCallback.replyAsync(0) {
                unit
            }
        }

        // Then
        assertEquals(unit, result.unwrap())
    }

    @Test
    fun shouldFallBackToTheCauseMessageWhenTheExceptionHasNone() {
        // Given
        val causeMessage = "underlying cause"

        // When
        val result = callbackApiCall<ResultData<String>> {
            TestCallback.replyAsync<String>(0) {
                throw Exception(null, Exception(causeMessage))
            }
        }

        // Then
        val failure = result.unwrapFailure()
        assertEquals(causeMessage, failure.exceptionMessage)
    }

    @Test
    fun shouldReportSynchronousFailuresTheSameWayAsAsynchronousOnes() {
        // Given
        val errorMessage = "sync error"

        // When
        val result = callbackApiCall<ResultData<String>> {
            TestCallback.reply<String>(0) {
                throw Exception(errorMessage)
            }
        }

        // Then
        val failure = result.unwrapFailure()
        assertContains(failure.exceptionType, Exception::class.simpleName!!)
        assertEquals(errorMessage, failure.exceptionMessage)
    }

    @Test
    fun shouldHandleCallbackFailure() {
        // Given
        val errorMessage = "test error"

        // When
        val result = callbackApiCall<ResultData<String>> {
            TestCallback.replyAsync<String>(0) {
                throw Exception(errorMessage)
            }
        }

        // Then
        val failure = result.unwrapFailure()
        assertContains(failure.exceptionType, Exception::class.simpleName!!)
        assertEquals(errorMessage, failure.exceptionMessage)
    }
}