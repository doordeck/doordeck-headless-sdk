package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.USER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountResourceImplTest : MockTest() {

    @Test
    fun shouldRefreshToken() = runTest {
        val response = AccountResourceImpl.refreshToken("")
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRefreshTokenAsync() = runTest {
        val response = AccountResourceImpl.refreshTokenAsync("").await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        val response = AccountResourceImpl.refreshToken()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRefreshTokenUsingContextAsync() = runTest {
        val response = AccountResourceImpl.refreshTokenAsync().await()
        assertEquals(TOKEN_RESPONSE, response)
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
        val response = AccountResourceImpl.registerEphemeralKey(byteArrayOf())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyAsync() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyAsync(byteArrayOf()).await()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        val response = AccountResourceImpl.registerEphemeralKey()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContextAsync() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyAsync().await()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationAsync() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthenticationAsync(byteArrayOf()).await()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContextAsync() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthenticationAsync().await()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        val response = AccountResourceImpl.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationAsync() = runTest {
        val response = AccountResourceImpl.verifyEphemeralKeyRegistrationAsync("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()).await()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        val response = AccountResourceImpl.verifyEphemeralKeyRegistration("")
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContextAsync() = runTest {
        val response = AccountResourceImpl.verifyEphemeralKeyRegistrationAsync("").await()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
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
        val response = AccountResourceImpl.getUserDetails()
        assertEquals(USER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldGetUserDetailsAsync() = runTest {
        val response = AccountResourceImpl.getUserDetailsAsync().await()
        assertEquals(USER_DETAILS_RESPONSE, response)
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