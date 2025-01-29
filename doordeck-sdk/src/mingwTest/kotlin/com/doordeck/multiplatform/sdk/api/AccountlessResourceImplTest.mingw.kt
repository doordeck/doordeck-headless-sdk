package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.api.model.LoginData
import com.doordeck.multiplatform.sdk.api.model.PasswordResetData
import com.doordeck.multiplatform.sdk.api.model.PasswordResetVerifyData
import com.doordeck.multiplatform.sdk.api.model.RegistrationData
import com.doordeck.multiplatform.sdk.api.model.VerifyEmailData
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
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
    fun shouldLoginJson() = runTest {
        val response = AccountlessResourceImpl.loginJson(LoginData("", "").toJson())
        assertEquals(TOKEN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRegister() = runTest {
        val response = AccountlessResourceImpl.registration("", "", "", false)
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRegisterJson() = runTest {
        val response = AccountlessResourceImpl.registrationJson(RegistrationData("", "", "", false).toJson())
        assertEquals(TOKEN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessResourceImpl.verifyEmail("")
    }

    @Test
    fun shouldVerifyEmailJson() = runTest {
        AccountlessResourceImpl.verifyEmailJson(VerifyEmailData("").toJson())
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessResourceImpl.passwordReset("")
    }

    @Test
    fun shouldResetPasswordJson() = runTest {
        AccountlessResourceImpl.passwordResetJson(PasswordResetData("").toJson())
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        AccountlessResourceImpl.passwordResetVerify("", "", "")
    }

    @Test
    fun shouldVerifyResetPasswordJson() = runTest {
        AccountlessResourceImpl.passwordResetVerifyJson(PasswordResetVerifyData("", "", "").toJson())
    }
}