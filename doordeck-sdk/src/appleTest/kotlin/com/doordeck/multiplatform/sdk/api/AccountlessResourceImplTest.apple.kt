package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest {

    private val accountless = AccountlessResourceImpl(AccountlessClient(TEST_HTTP_CLIENT))

    @Test
    fun shouldLogin() = runTest {
        accountless.login("", "")
    }

    @Test
    fun shouldRegister() = runTest {
        accountless.registration("", "", "", false)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        accountless.verifyEmail("")
    }
}