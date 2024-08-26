package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.future.await
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
    fun shouldRefreshTokenFuture() = runTest {
        account.refreshTokenFuture("").await()
    }

    @Test
    fun shouldLogout() = runTest {
        account.logout()
    }

    @Test
    fun shouldLogoutFuture() = runTest {
        account.logoutFuture().await()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        account.registerEphemeralKey(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyFuture() = runTest {
        account.registerEphemeralKeyFuture(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationFuture() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthenticationFuture(byteArrayOf()).await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        account.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationFuture() = runTest {
        account.verifyEphemeralKeyRegistrationFuture("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()).await()
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        account.reverifyEmail()
    }

    @Test
    fun shouldReverifyEmailFuture() = runTest {
        account.reverifyEmailFuture().await()
    }

    @Test
    fun shouldChangePassword() = runTest {
        account.changePassword("", "")
    }

    @Test
    fun shouldChangePasswordFuture() = runTest {
        account.changePasswordFuture("", "").await()
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        account.getUserDetails()
    }

    @Test
    fun shouldGetUserDetailsFuture() = runTest {
        account.getUserDetailsFuture().await()
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        account.updateUserDetails("")
    }

    @Test
    fun shouldUpdateUserDetailsFuture() = runTest {
        account.updateUserDetailsFuture("").await()
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        account.deleteAccount()
    }

    @Test
    fun shouldDeleteAccountFuture() = runTest {
        account.deleteAccountFuture().await()
    }
}