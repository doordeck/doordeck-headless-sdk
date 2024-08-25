package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
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
    fun shouldLogout() = runTest {
        account.logout()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        account.registerEphemeralKey(byteArrayOf())
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf())
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        account.verifyEphemeralKeyRegistration("", byteArrayOf())
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
    fun shouldGetUserDetails() = runTest {
        account.getUserDetails()
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        account.updateUserDetails("")
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        account.deleteAccount()
    }
}