package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.api.model.LoginData
import com.doordeck.multiplatform.sdk.api.model.RegistrationData
import com.doordeck.multiplatform.sdk.api.model.VerifyEmailData
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest {
    
    private val accountless = AccountlessResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldLogin() = runTest {
        accountless.login("", "")
    }

    @Test
    fun shouldLoginJson() = runTest {
        accountless.loginJson(LoginData("", "").toJson())
    }

    @Test
    fun shouldRegister() = runTest {
        accountless.registration("", "", "", false)
    }

    @Test
    fun shouldRegisterJson() = runTest {
        accountless.registrationJson(RegistrationData("", "", "", false).toJson())
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        accountless.verifyEmail("")
    }

    @Test
    fun shouldVerifyEmailJson() = runTest {
        accountless.verifyEmailJson(VerifyEmailData("").toJson())
    }
}