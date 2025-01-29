package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountlessResourceImplTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        val response = AccountlessResourceImpl.login("", "").await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRegister() = runTest {
        val response = AccountlessResourceImpl.registration("", "", "", false).await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessResourceImpl.verifyEmail("").await()
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessResourceImpl.passwordReset("").await()
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        AccountlessResourceImpl.passwordResetVerify("", "", "").await()
    }
}