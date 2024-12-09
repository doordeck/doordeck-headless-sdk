package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.HttpClient
import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HelperResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        HttpClient.overrideClient(TEST_HTTP_CLIENT)
        CloudHttpClient.overrideClient(TEST_HTTP_CLIENT)
    }

    @Test
    fun shouldUploadPlatformLogo() = runTest {
        HelperResourceImpl.uploadPlatformLogo(DEFAULT_APPLICATION_ID, "", byteArrayOf())
    }

    @Test
    fun shouldUploadPlatformLogoAsync() = runTest {
        HelperResourceImpl.uploadPlatformLogoAsync(DEFAULT_APPLICATION_ID, "", byteArrayOf()).await()
    }
}