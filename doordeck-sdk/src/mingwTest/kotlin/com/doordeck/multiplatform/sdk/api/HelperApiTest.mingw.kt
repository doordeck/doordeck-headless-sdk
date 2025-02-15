package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.model.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HelperApiTest : MockTest() {

    @Test
    fun shouldUploadPlatformLogo() = runTest {
        HelperApi.uploadPlatformLogo(DEFAULT_APPLICATION_ID, "", byteArrayOf())
    }

    @Test
    fun shouldUploadPlatformLogoJson() = runTest {
        HelperApi.uploadPlatformLogoJson(UploadPlatformLogoData(DEFAULT_APPLICATION_ID, "", byteArrayOf().encodeByteArrayToBase64()).toJson())
    }
}