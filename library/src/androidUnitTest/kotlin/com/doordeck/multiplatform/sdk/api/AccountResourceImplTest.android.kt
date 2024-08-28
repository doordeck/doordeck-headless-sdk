package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test

class AccountResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val account = AccountResourceImpl(TEST_HTTP_CLIENT, contextManager)

    @Test
    fun shouldRefreshToken() = runTest {
        account.refreshToken("")
    }

    @Test
    fun shouldRefreshTokenAsync() = runTest {
        account.refreshTokenAsync("").await()
    }

    @Test
    fun shouldLogout() = runTest {
        account.logout()
    }

    @Test
    fun shouldLogoutAsync() = runTest {
        account.logoutAsync().await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        account.registerEphemeralKey(byteArrayOf())
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldRegisterEphemeralKeyAsync() = runTest {
        account.registerEphemeralKeyAsync(byteArrayOf()).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationAsync() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthenticationAsync(byteArrayOf()).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        account.verifyEphemeralKeyRegistration("", byteArrayOf())
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldVerifyEphemeralKeyRegistrationAsync() = runTest {
        account.verifyEphemeralKeyRegistrationAsync("", byteArrayOf()).await()
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