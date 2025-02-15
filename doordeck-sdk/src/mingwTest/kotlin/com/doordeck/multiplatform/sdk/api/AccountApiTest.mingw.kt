package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.USER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.model.ChangePasswordData
import com.doordeck.multiplatform.sdk.model.RefreshTokenData
import com.doordeck.multiplatform.sdk.model.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.model.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.model.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
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
    fun shouldRefreshTokenJson() = runTest {
        val response = AccountApi.refreshTokenJson(RefreshTokenData("").toJson())
        assertEquals(TOKEN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        val response = AccountApi.refreshToken()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRefreshTokenUsingContextJson() = runTest {
        val response = AccountApi.refreshTokenJson()
        assertEquals(TOKEN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldLogout() = runTest {
        AccountApi.logout()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        val response = AccountApi.registerEphemeralKey(byteArrayOf())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyJson() = runTest {
        val response = AccountApi.registerEphemeralKeyJson(RegisterEphemeralKeyData(byteArrayOf().encodeByteArrayToBase64()).toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        val response = AccountApi.registerEphemeralKey()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContextJson() = runTest {
        val response = AccountApi.registerEphemeralKeyJson()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationJson() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthenticationJson(
            RegisterEphemeralKeyWithSecondaryAuthenticationData(byteArrayOf().encodeByteArrayToBase64()).toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthentication()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContextJson() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthenticationJson(
            RegisterEphemeralKeyWithSecondaryAuthenticationData().toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationJson() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistrationJson(VerifyEphemeralKeyRegistrationData("", TEST_MAIN_USER_PRIVATE_KEY).toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistration("")
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContextJson() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistrationJson(VerifyEphemeralKeyRegistrationData("").toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), response)
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
    fun shouldChangePasswordJson() = runTest {
        AccountApi.changePasswordJson(ChangePasswordData("", "").toJson())
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        val response = AccountApi.getUserDetails()
        assertEquals(USER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldGetUserDetailsJson() = runTest {
        val response = AccountApi.getUserDetailsJson()
        assertEquals(USER_DETAILS_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        AccountApi.updateUserDetails("")
    }

    @Test
    fun shouldUpdateUserDetailsJson() = runTest {
        AccountApi.updateUserDetailsJson(UpdateUserDetailsData("").toJson())
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        AccountApi.deleteAccount()
    }
}