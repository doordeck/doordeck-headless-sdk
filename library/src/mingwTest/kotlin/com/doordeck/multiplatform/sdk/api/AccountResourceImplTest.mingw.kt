package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.api.model.ChangePasswordData
import com.doordeck.multiplatform.sdk.api.model.RefreshTokenData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.api.model.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.api.model.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val account = AccountResourceImpl(TEST_HTTP_CLIENT, contextManager)

    init {
        LibsodiumInitializer.initializeWithCallback {  }
    }

    @Test
    fun shouldRefreshToken() = runTest {
        account.refreshToken("")
    }

    @Test
    fun shouldRefreshTokenJson() = runTest {
        account.refreshTokenJson(RefreshTokenData("").toJson())
    }

    @Test
    fun shouldLogout() = runTest {
        account.logout()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        account.registerEphemeralKey(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyJson() = runTest {
        account.registerEphemeralKeyJson(RegisterEphemeralKeyData(byteArrayOf().encodeByteArrayToBase64()).toJson())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationJson() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthenticationJson(RegisterEphemeralKeyWithSecondaryAuthenticationData(byteArrayOf().encodeByteArrayToBase64()).toJson())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        account.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationJson() = runTest {
        account.verifyEphemeralKeyRegistrationJson(VerifyEphemeralKeyRegistrationData("", TEST_MAIN_USER_PRIVATE_KEY).toJson())
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        account.reverifyEmail()
    }

    @Test
    fun shouldChangePassword() = runTest {
        account.changePassword("", "")
    }

    @Test
    fun shouldChangePasswordJson() = runTest {
        account.changePasswordJson(ChangePasswordData("", "").toJson())
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        account.getUserDetails()
    }

    @Test
    fun shouldGetUserDetailsJson() = runTest {
        account.getUserDetailsJson()
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        account.updateUserDetails("")
    }

    @Test
    fun shouldUpdateUserDetailsJson() = runTest {
        account.updateUserDetailsJson(UpdateUserDetailsData("").toJson())
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        account.deleteAccount()
    }
}