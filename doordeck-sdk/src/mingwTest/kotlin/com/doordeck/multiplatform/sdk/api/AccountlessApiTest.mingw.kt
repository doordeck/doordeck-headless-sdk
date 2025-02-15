package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.model.LoginData
import com.doordeck.multiplatform.sdk.model.PasswordResetData
import com.doordeck.multiplatform.sdk.model.PasswordResetVerifyData
import com.doordeck.multiplatform.sdk.model.RegistrationData
import com.doordeck.multiplatform.sdk.model.VerifyEmailData
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
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
    fun shouldLoginJson() = runTest {
        val response = AccountlessApi.loginJson(LoginData("", "").toJson())
        assertEquals(TOKEN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRegister() = runTest {
        val response = AccountlessApi.registration("", "", "", false)
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRegisterJson() = runTest {
        val response = AccountlessApi.registrationJson(RegistrationData("", "", "", false).toJson())
        assertEquals(TOKEN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessApi.verifyEmail("")
    }

    @Test
    fun shouldVerifyEmailJson() = runTest {
        AccountlessApi.verifyEmailJson(VerifyEmailData("").toJson())
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessApi.passwordReset("")
    }

    @Test
    fun shouldResetPasswordJson() = runTest {
        AccountlessApi.passwordResetJson(PasswordResetData("").toJson())
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        AccountlessApi.passwordResetVerify("", "", "")
    }

    @Test
    fun shouldVerifyResetPasswordJson() = runTest {
        AccountlessApi.passwordResetVerifyJson(PasswordResetVerifyData("", "", "").toJson())
    }
}