package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.api.model.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.HelperClient
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.PlatformClient
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HelperResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val helper = HelperResourceImpl(
        HelperClient(
            httpClient = TEST_HTTP_CLIENT,
            accountlessClient = AccountlessClient(TEST_HTTP_CLIENT, contextManager),
            accountClient = AccountClient(TEST_HTTP_CLIENT, contextManager),
            platformClient = PlatformClient(TEST_HTTP_CLIENT),
            contextManagerImpl = contextManager
        )
    )

    @Test
    fun shouldUploadPlatformLogo() = runTest {
        helper.uploadPlatformLogo(DEFAULT_APPLICATION_ID, "", byteArrayOf())
    }

    @Test
    fun shouldUploadPlatformLogoJson() = runTest {
        helper.uploadPlatformLogoJson(UploadPlatformLogoData(DEFAULT_APPLICATION_ID, "", byteArrayOf().encodeByteArrayToBase64()).toJson())
    }
}