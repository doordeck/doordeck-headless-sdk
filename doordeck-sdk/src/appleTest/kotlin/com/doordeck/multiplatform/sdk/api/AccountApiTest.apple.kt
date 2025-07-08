package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.USER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountApiTest : MockTest() {

    @Test
    fun shouldRefreshToken() = runTest {
        val response = AccountApi.refreshToken("")
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        val response = AccountApi.refreshToken()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldLogout() = runTest {
        AccountApi.logout()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        val response = AccountApi.registerEphemeralKey(byteArrayOf(), byteArrayOf())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        val response = AccountApi.registerEphemeralKey()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthentication()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistration("")
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        AccountApi.reverifyEmail()
    }

    @Test
    fun shouldChangePassword() = runTest {
        AccountApi.changePassword("", "")
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        val response = AccountApi.getUserDetails()
        assertEquals(USER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        AccountApi.updateUserDetails("")
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        AccountApi.deleteAccount()
    }
}