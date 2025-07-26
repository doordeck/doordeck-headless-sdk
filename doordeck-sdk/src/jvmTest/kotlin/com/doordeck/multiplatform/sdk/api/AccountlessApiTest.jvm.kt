package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.model.data.toToken
import com.doordeck.multiplatform.sdk.randomUUID
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountlessApiTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        val response = AccountlessApi.login("", "")
        assertEquals(TOKEN_RESPONSE.toToken(), response)
    }

    @Test
    fun shouldLoginAsync() = runTest {
        val response = AccountlessApi.loginAsync("", "").await()
        assertEquals(TOKEN_RESPONSE.toToken(), response)
    }

    @Test
    fun shouldRegister() = runTest {
        val response = AccountlessApi.registration("", "", "", false)
        assertEquals(TOKEN_RESPONSE.toToken(), response)
    }

    @Test
    fun shouldRegisterAsync() = runTest {
        val response = AccountlessApi.registrationAsync("", "", "", false).await()
        assertEquals(TOKEN_RESPONSE.toToken(), response)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessApi.verifyEmail("")
    }

    @Test
    fun shouldVerifyEmailAsync() = runTest {
        AccountlessApi.verifyEmailAsync("").await()
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessApi.passwordReset("")
    }

    @Test
    fun shouldResetPasswordAsync() = runTest {
        AccountlessApi.passwordResetAsync("").await()
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        AccountlessApi.passwordResetVerify(randomUUID(), "", "")
    }

    @Test
    fun shouldVerifyResetPasswordAsync() = runTest {
        AccountlessApi.passwordResetVerifyAsync(randomUUID(), "", "").await()
    }
}