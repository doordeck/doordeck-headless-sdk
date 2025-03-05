package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest

open class IntegrationTest {

    @BeforeTest
    fun setupContext() = runTest {
        ContextManagerImpl.reset()
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        CloudHttpClient.overrideClient(TEST_CLOUD_CLIENT)
        HttpClient.overrideClient(TEST_HTTP_CLIENT)
        FusionHttpClient.overrideClient(TEST_FUSION_CLIENT)
    }
}