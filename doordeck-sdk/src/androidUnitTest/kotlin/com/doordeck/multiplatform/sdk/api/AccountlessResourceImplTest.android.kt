package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val accountless = AccountlessResourceImpl(AccountlessClient(TEST_HTTP_CLIENT, contextManager))

    @Test
    fun shouldLogin() = runTest {
        accountless.login("", "")
    }

    @Test
    fun shouldLoginAsync() = runTest {
        accountless.loginAsync("", "").await()
    }

    @Test
    fun shouldRegister() = runTest {
        accountless.registration("", "", "", false)
    }

    @Test
    fun shouldRegisterAsync() = runTest {
        accountless.registrationAsync("", "", "", false).await()
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        accountless.verifyEmail("")
    }

    @Test
    fun shouldVerifyEmailAsync() = runTest {
        accountless.verifyEmailAsync("").await()
    }
}