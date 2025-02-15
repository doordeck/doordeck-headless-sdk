package com.doordeck.multiplatform.sdk.api.responses

import com.doordeck.multiplatform.sdk.model.responses.ResponseError
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ResponseErrorTest {

    @Test
    fun shouldHandleErrorWithMajorAndMinorCodes() = runTest {
        // Given
        val json = "{\"code\":404.12,\"message\":\"test\"}"

        // When
        val result = json.fromJson<ResponseError>()

        // Then
        assertEquals(result.code, 404.12)
        assertEquals(result.message, "test")
        assertEquals(result.getMajorCode(), 404)
        assertEquals(result.getMinorCode(), 12)
    }

    @Test
    fun shouldHandleErrorWithMajorCode() = runTest {
        // Given
        val json = "{\"code\":404,\"message\":\"test\"}"

        // When
        val result = json.fromJson<ResponseError>()

        // Then
        assertEquals(result.code, 404.0)
        assertEquals(result.message, "test")
        assertEquals(result.getMajorCode(), 404)
        assertNull(result.getMinorCode())
    }

    @Test
    fun shouldHandleErrorWithMajorAndNullMinorCodes() = runTest {
        // Given
        val json = "{\"code\":404.0,\"message\":\"test\"}"

        // When
        val result = json.fromJson<ResponseError>()

        // Then
        assertEquals(result.code, 404.0)
        assertEquals(result.message, "test")
        assertEquals(result.getMajorCode(), 404)
        assertNull(result.getMinorCode())
    }
}