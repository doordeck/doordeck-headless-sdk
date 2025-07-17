package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.USER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.future.await
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
    fun shouldRefreshTokenAsync() = runTest {
        val response = AccountApi.refreshTokenAsync("").await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        val response = AccountApi.refreshToken()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldRefreshTokenUsingContextAsync() = runTest {
        val response = AccountApi.refreshTokenAsync().await()
        assertEquals(TOKEN_RESPONSE, response)
    }

    @Test
    fun shouldLogout() = runTest {
        AccountApi.logout()
    }

    @Test
    fun shouldLogoutAsync() = runTest {
        AccountApi.logoutAsync().await()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        val response = AccountApi.registerEphemeralKey(
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray().toPrivateKey()
        )
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyAsync() = runTest {
        val response = AccountApi.registerEphemeralKeyAsync(
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray().toPrivateKey()
        ).await()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        val response = AccountApi.registerEphemeralKey()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContextAsync() = runTest {
        val response = AccountApi.registerEphemeralKeyAsync().await()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthentication(
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey()
        )
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationAsync() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthenticationAsync(
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey()
        ).await()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthentication()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContextAsync() = runTest {
        val response = AccountApi.registerEphemeralKeyWithSecondaryAuthenticationAsync().await()
        assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistration(
            code = "",
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray().toPrivateKey()
        )
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationAsync() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistrationAsync(
            code = "",
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray().toPrivateKey()
        ).await()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistration("")
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContextAsync() = runTest {
        val response = AccountApi.verifyEphemeralKeyRegistrationAsync("").await()
        assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE, response)
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        AccountApi.reverifyEmail()
    }

    @Test
    fun shouldReverifyEmailAsync() = runTest {
        AccountApi.reverifyEmailAsync().await()
    }

    @Test
    fun shouldChangePassword() = runTest {
        AccountApi.changePassword("", "")
    }

    @Test
    fun shouldChangePasswordAsync() = runTest {
        AccountApi.changePasswordAsync("", "").await()
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        val response = AccountApi.getUserDetails()
        assertEquals(USER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldGetUserDetailsAsync() = runTest {
        val response = AccountApi.getUserDetailsAsync().await()
        assertEquals(USER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        AccountApi.updateUserDetails("")
    }

    @Test
    fun shouldUpdateUserDetailsAsync() = runTest {
        AccountApi.updateUserDetailsAsync("").await()
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        AccountApi.deleteAccount()
    }

    @Test
    fun shouldDeleteAccountAsync() = runTest {
        AccountApi.deleteAccountAsync().await()
    }
}