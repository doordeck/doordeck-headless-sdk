package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
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
    fun shouldRegister() = runTest {
        val response = AccountlessResourceImpl.registration("", "", "", false)
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessResourceImpl.verifyEmail("")
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessResourceImpl.passwordReset("")
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        AccountlessResourceImpl.passwordResetVerify("", "", "")
    }
}