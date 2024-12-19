package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountResourceImplTest : MockTest() {

    @Test
    fun shouldRefreshToken() = runTest {
        AccountResourceImpl.refreshToken("")
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        AccountResourceImpl.refreshToken()
    }

    @Test
    fun shouldLogout() = runTest {
        AccountResourceImpl.logout()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        AccountResourceImpl.registerEphemeralKey(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        AccountResourceImpl.registerEphemeralKey()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistration("")
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        AccountResourceImpl.reverifyEmail()
    }

    @Test
    fun shouldChangePassword() = runTest {
        AccountResourceImpl.changePassword("", "")
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        AccountResourceImpl.getUserDetails()
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        AccountResourceImpl.updateUserDetails("")
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        AccountResourceImpl.deleteAccount()
    }
}