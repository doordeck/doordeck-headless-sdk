package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        AccountlessResourceImpl.login("", "")
    }

    @Test
    fun shouldLoginAsync() = runTest {
        AccountlessResourceImpl.loginAsync("", "").await()
    }

    @Test
    fun shouldRegister() = runTest {
        AccountlessResourceImpl.registration("", "", "", false)
    }

    @Test
    fun shouldRegisterAsync() = runTest {
        AccountlessResourceImpl.registrationAsync("", "", "", false).await()
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