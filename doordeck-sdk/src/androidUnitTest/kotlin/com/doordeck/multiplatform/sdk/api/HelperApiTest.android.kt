package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HelperApiTest : MockTest() {

    @Test
    fun shouldUploadPlatformLogo() = runTest {
        HelperApi.uploadPlatformLogo(DEFAULT_APPLICATION_ID, "", byteArrayOf())
    }

    @Test
    fun shouldUploadPlatformLogoAsync() = runTest {
        HelperApi.uploadPlatformLogoAsync(DEFAULT_APPLICATION_ID, "", byteArrayOf()).await()
    }
}