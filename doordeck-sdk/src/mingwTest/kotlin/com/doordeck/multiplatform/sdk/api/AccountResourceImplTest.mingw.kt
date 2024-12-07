package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.api.model.ChangePasswordData
import com.doordeck.multiplatform.sdk.api.model.RefreshTokenData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.api.model.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.api.model.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val account = AccountResourceImpl(AccountClient(TEST_HTTP_CLIENT, contextManager))

    init {
        LibsodiumInitializer.initializeWithCallback {  }
        contextManager.setKeyPair(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
        contextManager.setRefreshToken("")
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
    fun shouldRefreshTokenUsingContext() = runTest {
        account.refreshToken()
    }

    @Test
    fun shouldRefreshTokenUsingContextJson() = runTest {
        account.refreshTokenJson()
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
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        account.registerEphemeralKey()
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContextJson() = runTest {
        account.registerEphemeralKeyJson()
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
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContextJson() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthenticationJson(RegisterEphemeralKeyWithSecondaryAuthenticationData().toJson())
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
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        account.verifyEphemeralKeyRegistration("")
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContextJson() = runTest {
        account.verifyEphemeralKeyRegistrationJson(VerifyEphemeralKeyRegistrationData("").toJson())
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