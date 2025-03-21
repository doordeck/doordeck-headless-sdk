package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.UNIT_RESULT_DATA
import com.doordeck.multiplatform.sdk.capturedCallback
import com.doordeck.multiplatform.sdk.model.data.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class HelperApiTest : CallbackTest() {

    @Test
    fun shouldUploadPlatformLogo() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            HelperApi.uploadPlatformLogo(
                data = UploadPlatformLogoData(DEFAULT_APPLICATION_ID, "", byteArrayOf().encodeByteArrayToBase64()).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }
}