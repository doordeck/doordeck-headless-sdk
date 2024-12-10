package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val accountless = AccountlessResourceImpl(AccountlessClient(TEST_HTTP_CLIENT, contextManager))

    @Test
    fun shouldLogin() = runTest {
        accountless.login("", "").await()
    }

    @Test
    fun shouldRegister() = runTest {
        accountless.registration("", "", "", false).await()
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        accountless.verifyEmail("").await()
    }
}