package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.TEST_MOCK_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        CloudHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
    }

    @Test
    fun shouldLogin() = runTest {
        AccountlessResourceImpl.login("", "")
    }

    @Test
    fun shouldRegister() = runTest {
        AccountlessResourceImpl.registration("", "", "", false)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessResourceImpl.verifyEmail("")
    }
}