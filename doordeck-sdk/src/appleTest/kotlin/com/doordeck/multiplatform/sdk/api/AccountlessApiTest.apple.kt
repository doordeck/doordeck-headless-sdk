package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountlessApiTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        val response = AccountlessApi.login("", "")
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRegister() = runTest {
        val response = AccountlessApi.registration("", "", "", false)
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessApi.verifyEmail("")
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessApi.passwordReset("")
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        AccountlessApi.passwordResetVerify("", "", "")
    }
}