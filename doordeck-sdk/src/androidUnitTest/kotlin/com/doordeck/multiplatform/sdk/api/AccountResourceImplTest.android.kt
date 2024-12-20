package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val account = AccountResourceImpl(AccountClient(TEST_HTTP_CLIENT, contextManager))

    init {
        contextManager.setKeyPair(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
        contextManager.setRefreshToken("")
    }

    @Test
    fun shouldRefreshToken() = runTest {
        account.refreshToken("")
    }

    @Test
    fun shouldRefreshTokenAsync() = runTest {
        account.refreshTokenAsync("").await()
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        account.refreshToken()
    }

    @Test
    fun shouldRefreshTokenUsingContextAsync() = runTest {
        account.refreshTokenAsync().await()
    }

    @Test
    fun shouldLogout() = runTest {
        account.logout()
    }

    @Test
    fun shouldLogoutAsync() = runTest {
        account.logoutAsync().await()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        account.registerEphemeralKey(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyAsync() = runTest {
        account.registerEphemeralKeyAsync(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        account.registerEphemeralKey()
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContextAsync() = runTest {
        account.registerEphemeralKeyAsync().await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationAsync() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthenticationAsync(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContextAsync() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthenticationAsync().await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        account.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationAsync() = runTest {
        account.verifyEphemeralKeyRegistrationAsync("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()).await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        account.verifyEphemeralKeyRegistration("")
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContextAsync() = runTest {
        account.verifyEphemeralKeyRegistrationAsync("").await()
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        account.reverifyEmail()
    }

    @Test
    fun shouldReverifyEmailAsync() = runTest {
        account.reverifyEmailAsync().await()
    }

    @Test
    fun shouldChangePassword() = runTest {
        account.changePassword("", "")
    }

    @Test
    fun shouldChangePasswordAsync() = runTest {
        account.changePasswordAsync("", "").await()
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        account.getUserDetails()
    }

    @Test
    fun shouldGetUserDetailsAsync() = runTest {
        account.getUserDetailsAsync().await()
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        account.updateUserDetails("")
    }

    @Test
    fun shouldUpdateUserDetailsAsync() = runTest {
        account.updateUserDetailsAsync("").await()
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        account.deleteAccount()
    }

    @Test
    fun shouldDeleteAccountAsync() = runTest {
        account.deleteAccountAsync().await()
    }
}