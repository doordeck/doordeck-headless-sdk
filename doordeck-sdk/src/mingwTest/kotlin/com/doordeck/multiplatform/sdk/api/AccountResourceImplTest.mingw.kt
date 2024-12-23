package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.api.model.ChangePasswordData
import com.doordeck.multiplatform.sdk.api.model.RefreshTokenData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.api.model.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.api.model.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountResourceImplTest : MockTest() {

    @Test
    fun shouldRefreshToken() = runTest {
        AccountResourceImpl.refreshToken("")
    }

    @Test
    fun shouldRefreshTokenJson() = runTest {
        AccountResourceImpl.refreshTokenJson(RefreshTokenData("").toJson())
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        AccountResourceImpl.refreshToken()
    }

    @Test
    fun shouldRefreshTokenUsingContextJson() = runTest {
        AccountResourceImpl.refreshTokenJson()
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
    fun shouldRegisterEphemeralKeyJson() = runTest {
        AccountResourceImpl.registerEphemeralKeyJson(RegisterEphemeralKeyData(byteArrayOf().encodeByteArrayToBase64()).toJson())
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        AccountResourceImpl.registerEphemeralKey()
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContextJson() = runTest {
        AccountResourceImpl.registerEphemeralKeyJson()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationJson() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthenticationJson(RegisterEphemeralKeyWithSecondaryAuthenticationData(byteArrayOf().encodeByteArrayToBase64()).toJson())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContextJson() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthenticationJson(RegisterEphemeralKeyWithSecondaryAuthenticationData().toJson())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationJson() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistrationJson(VerifyEphemeralKeyRegistrationData("", TEST_MAIN_USER_PRIVATE_KEY).toJson())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistration("")
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContextJson() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistrationJson(VerifyEphemeralKeyRegistrationData("").toJson())
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
        AccountResourceImpl.getUserDetails()
    }

    @Test
    fun shouldGetUserDetailsJson() = runTest {
        AccountResourceImpl.getUserDetailsJson()
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