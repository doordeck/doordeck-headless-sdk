package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountResourceImplTest : MockTest() {

    @Test
    fun shouldRefreshToken() = runTest {
        AccountResourceImpl.refreshToken("")
    }

    @Test
    fun shouldRefreshTokenAsync() = runTest {
        AccountResourceImpl.refreshTokenAsync("").await()
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        AccountResourceImpl.refreshToken()
    }

    @Test
    fun shouldRefreshTokenUsingContextAsync() = runTest {
        AccountResourceImpl.refreshTokenAsync().await()
    }

    @Test
    fun shouldLogout() = runTest {
        AccountResourceImpl.logout()
    }

    @Test
    fun shouldLogoutAsync() = runTest {
        AccountResourceImpl.logoutAsync().await()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        AccountResourceImpl.registerEphemeralKey(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyAsync() = runTest {
        AccountResourceImpl.registerEphemeralKeyAsync(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        AccountResourceImpl.registerEphemeralKey()
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContextAsync() = runTest {
        AccountResourceImpl.registerEphemeralKeyAsync().await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationAsync() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthenticationAsync(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContextAsync() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthenticationAsync().await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationAsync() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistrationAsync("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()).await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistration("")
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContextAsync() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistrationAsync("").await()
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        AccountResourceImpl.reverifyEmail()
    }

    @Test
    fun shouldReverifyEmailAsync() = runTest {
        AccountResourceImpl.reverifyEmailAsync().await()
    }

    @Test
    fun shouldChangePassword() = runTest {
        AccountResourceImpl.changePassword("", "")
    }

    @Test
    fun shouldChangePasswordAsync() = runTest {
        AccountResourceImpl.changePasswordAsync("", "").await()
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        AccountResourceImpl.getUserDetails()
    }

    @Test
    fun shouldGetUserDetailsAsync() = runTest {
        AccountResourceImpl.getUserDetailsAsync().await()
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        AccountResourceImpl.updateUserDetails("")
    }

    @Test
    fun shouldUpdateUserDetailsAsync() = runTest {
        AccountResourceImpl.updateUserDetailsAsync("").await()
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        AccountResourceImpl.deleteAccount()
    }

    @Test
    fun shouldDeleteAccountAsync() = runTest {
        AccountResourceImpl.deleteAccountAsync().await()
    }
}