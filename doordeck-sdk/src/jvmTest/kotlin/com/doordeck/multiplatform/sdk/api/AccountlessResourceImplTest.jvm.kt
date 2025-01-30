package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountlessResourceImplTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        val response = AccountlessResourceImpl.login("", "")
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldLoginAsync() = runTest {
        val response = AccountlessResourceImpl.loginAsync("", "").await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRegister() = runTest {
        val response = AccountlessResourceImpl.registration("", "", "", false)
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRegisterAsync() = runTest {
        val response = AccountlessResourceImpl.registrationAsync("", "", "", false).await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessResourceImpl.verifyEmail("")
    }

    @Test
    fun shouldVerifyEmailAsync() = runTest {
        AccountlessResourceImpl.verifyEmailAsync("").await()
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessResourceImpl.passwordReset("")
    }

    @Test
    fun shouldResetPasswordAsync() = runTest {
        AccountlessResourceImpl.passwordResetAsync("").await()
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        AccountlessResourceImpl.passwordResetVerify("", "", "")
    }

    @Test
    fun shouldVerifyResetPasswordAsync() = runTest {
        AccountlessResourceImpl.passwordResetVerifyAsync("", "", "").await()
    }
}