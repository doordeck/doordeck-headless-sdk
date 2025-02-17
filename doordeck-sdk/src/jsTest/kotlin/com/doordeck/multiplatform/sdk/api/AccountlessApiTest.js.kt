package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountlessApiTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        val response = AccountlessApi.login("", "").await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRegister() = runTest {
        val response = AccountlessApi.registration("", "", "", false).await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessApi.verifyEmail("").await()
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessApi.passwordReset("").await()
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        AccountlessApi.passwordResetVerify("", "", "").await()
    }
}