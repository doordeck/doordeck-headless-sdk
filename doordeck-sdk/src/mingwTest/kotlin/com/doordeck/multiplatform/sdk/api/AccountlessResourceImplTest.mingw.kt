package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.api.model.LoginData
import com.doordeck.multiplatform.sdk.api.model.PasswordResetData
import com.doordeck.multiplatform.sdk.api.model.RegistrationData
import com.doordeck.multiplatform.sdk.api.model.VerifyEmailData
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest : MockTest() {

     @Test
    fun shouldLogin() = runTest {
        AccountlessResourceImpl.login("", "")
    }

    @Test
    fun shouldLoginJson() = runTest {
        AccountlessResourceImpl.loginJson(LoginData("", "").toJson())
    }

    @Test
    fun shouldRegister() = runTest {
        AccountlessResourceImpl.registration("", "", "", false)
    }

    @Test
    fun shouldRegisterJson() = runTest {
        AccountlessResourceImpl.registrationJson(RegistrationData("", "", "", false).toJson())
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
}