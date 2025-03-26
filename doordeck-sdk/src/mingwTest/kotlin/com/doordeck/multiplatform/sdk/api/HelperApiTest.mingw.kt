package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.model.data.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HelperApiTest : CallbackTest() {

    @Test
    fun shouldUploadPlatformLogo() = runTest {
        callbackTest(
            apiCall = {
                HelperApi.uploadPlatformLogo(
                    data = UploadPlatformLogoData(DEFAULT_APPLICATION_ID, "", byteArrayOf().encodeByteArrayToBase64()).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }
}