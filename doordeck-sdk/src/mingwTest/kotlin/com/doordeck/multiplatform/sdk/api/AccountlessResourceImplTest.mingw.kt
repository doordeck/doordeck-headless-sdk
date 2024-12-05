package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.api.model.LoginData
import com.doordeck.multiplatform.sdk.api.model.RegistrationData
import com.doordeck.multiplatform.sdk.api.model.VerifyEmailData
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val accountless = AccountlessResourceImpl(AccountlessClient(TEST_HTTP_CLIENT, contextManager))

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