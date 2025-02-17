package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest

open class MockTest {

    @BeforeTest
    fun setupContext() = runTest {
        ContextManagerImpl.reset()
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        ContextManagerImpl.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        ContextManagerImpl.setOperationContext("", emptyList(), TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
        ContextManagerImpl.setRefreshToken("")
        CloudHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
        HttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
        FusionHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
    }
}