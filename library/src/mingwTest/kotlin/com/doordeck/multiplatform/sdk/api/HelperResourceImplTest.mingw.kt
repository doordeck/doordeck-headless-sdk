package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.api.model.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HelperResourceImplTest {

    private val helper = HelperResourceImpl(TEST_HTTP_CLIENT, TEST_HTTP_CLIENT)

    @Test
    fun shouldUploadPlatformLogo() = runTest {
        helper.uploadPlatformLogo(DEFAULT_APPLICATION_ID, "", byteArrayOf())
    }

    @Test
    fun shouldUploadPlatformLogoJson() = runTest {
        helper.uploadPlatformLogoJson(UploadPlatformLogoData(DEFAULT_APPLICATION_ID, "", byteArrayOf().encodeByteArrayToBase64()).toJson())
    }
}