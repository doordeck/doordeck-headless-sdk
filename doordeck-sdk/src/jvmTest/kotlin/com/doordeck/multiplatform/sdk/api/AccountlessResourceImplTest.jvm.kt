package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        CloudHttpClient.overrideClient(TEST_HTTP_CLIENT)
    }

    @Test
    fun shouldLogin() = runTest {
        AccountlessResourceImpl.login("", "")
    }

    @Test
    fun shouldLoginAsync() = runTest {
        AccountlessResourceImpl.loginAsync("", "").await()
    }

    @Test
    fun shouldRegister() = runTest {
        AccountlessResourceImpl.registration("", "", "", false)
    }

    @Test
    fun shouldRegisterAsync() = runTest {
        AccountlessResourceImpl.registrationAsync("", "", "", false).await()
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessResourceImpl.verifyEmail("")
    }

    @Test
    fun shouldVerifyEmailAsync() = runTest {
        AccountlessResourceImpl.verifyEmailAsync("").await()
    }
}