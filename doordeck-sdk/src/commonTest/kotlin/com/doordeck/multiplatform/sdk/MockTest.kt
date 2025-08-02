package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest

open class MockTest {

    @BeforeTest
    fun setupContext() = runTest {
        Context.reset()
        Context.setApiEnvironment(TEST_ENVIRONMENT)
        Context.setOperationContext("", emptyList(), TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), true)
        Context.setCloudRefreshToken("")
        CloudHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
        HttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
        FusionHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
    }
}