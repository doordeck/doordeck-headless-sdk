package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessResourceImplTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        AccountlessResourceImpl.login("", "")
    }

    @Test
    fun shouldRegister() = runTest {
        AccountlessResourceImpl.registration("", "", "", false)
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        AccountlessResourceImpl.verifyEmail("")
    }

    @Test
    fun shouldResetPassword() = runTest {
        AccountlessResourceImpl.passwordReset("")
    }
}