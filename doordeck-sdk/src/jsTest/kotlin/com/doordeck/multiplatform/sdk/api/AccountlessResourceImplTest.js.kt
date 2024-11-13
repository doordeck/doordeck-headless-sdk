package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest {

    private val accountless = AccountlessResourceImpl(TEST_HTTP_CLIENT)

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