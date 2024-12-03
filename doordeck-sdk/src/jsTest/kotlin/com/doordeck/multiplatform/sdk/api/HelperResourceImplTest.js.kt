package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.HelperClient
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.PlatformClient
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class HelperResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val helper = HelperResourceImpl(
        HelperClient(
            httpClient = TEST_HTTP_CLIENT,
            accountlessClient = AccountlessClient(TEST_HTTP_CLIENT),
            accountClient = AccountClient(TEST_HTTP_CLIENT, contextManager),
            platformClient = PlatformClient(TEST_HTTP_CLIENT),
            contextManagerImpl = contextManager,
            cryptoManager = CryptoManager
        )
    )

    @Test
    fun shouldUploadPlatformLogo() = runTest {
        helper.uploadPlatformLogo(DEFAULT_APPLICATION_ID, "", byteArrayOf()).await()
    }
}