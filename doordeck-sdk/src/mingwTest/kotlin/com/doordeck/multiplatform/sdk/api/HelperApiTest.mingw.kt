package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.responses.BasicServerTimeResponse
import com.doordeck.multiplatform.sdk.unwrap
import kotlinx.coroutines.test.runTest
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Clock

class HelperApiTest : CallbackTest() {

    @Test
    fun shouldGetServerTime() = runTest {
        // When
        val result = callbackApiCall<ResultData<BasicServerTimeResponse>> {
            HelperApi.serverTime(TestCallback)
        }.unwrap()

        // Then
        assertTrue {
            abs(result.now - Clock.System.now().epochSeconds) < 5
        }
    }
}