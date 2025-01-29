package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.USER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.api.model.ChangePasswordData
import com.doordeck.multiplatform.sdk.api.model.RefreshTokenData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.api.model.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.api.model.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
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
    fun shouldRefreshTokenJson() = runTest {
        val response = AccountResourceImpl.refreshTokenJson(RefreshTokenData("").toJson())
        assertEquals(TOKEN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        val response = AccountResourceImpl.refreshToken()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRefreshTokenUsingContextJson() = runTest {
        val response = AccountResourceImpl.refreshTokenJson()
        assertEquals(TOKEN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldLogout() = runTest {
        AccountResourceImpl.logout()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        val response = AccountResourceImpl.registerEphemeralKey(byteArrayOf())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyJson() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyJson(RegisterEphemeralKeyData(byteArrayOf().encodeByteArrayToBase64()).toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        val response = AccountResourceImpl.registerEphemeralKey()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContextJson() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyJson()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationJson() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthenticationJson(RegisterEphemeralKeyWithSecondaryAuthenticationData(byteArrayOf().encodeByteArrayToBase64()).toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContextJson() = runTest {
        val response = AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthenticationJson(RegisterEphemeralKeyWithSecondaryAuthenticationData().toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        val response = AccountResourceImpl.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationJson() = runTest {
        val response = AccountResourceImpl.verifyEphemeralKeyRegistrationJson(VerifyEphemeralKeyRegistrationData("", TEST_MAIN_USER_PRIVATE_KEY).toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        val response = AccountResourceImpl.verifyEphemeralKeyRegistration("")
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContextJson() = runTest {
        val response = AccountResourceImpl.verifyEphemeralKeyRegistrationJson(VerifyEphemeralKeyRegistrationData("").toJson())
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), response)
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
    fun shouldChangePasswordJson() = runTest {
        AccountResourceImpl.changePasswordJson(ChangePasswordData("", "").toJson())
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        val response = AccountResourceImpl.getUserDetails()
        assertEquals(USER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldGetUserDetailsJson() = runTest {
        val response = AccountResourceImpl.getUserDetailsJson()
        assertEquals(USER_DETAILS_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        AccountResourceImpl.updateUserDetails("")
    }

    @Test
    fun shouldUpdateUserDetailsJson() = runTest {
        AccountResourceImpl.updateUserDetailsJson(UpdateUserDetailsData("").toJson())
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        AccountResourceImpl.deleteAccount()
    }
}