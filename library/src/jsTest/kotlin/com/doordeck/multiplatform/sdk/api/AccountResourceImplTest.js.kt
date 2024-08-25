package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.await
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
        account.refreshToken("").await()
    }

    @Test
    fun shouldLogout() = runTest {
        account.logout().await()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        account.registerEphemeralKey(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf()).await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        account.verifyEphemeralKeyRegistration("", byteArrayOf()).await()
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        account.reverifyEmail().await()
    }

    @Test
    fun shouldChangePassword() = runTest {
        account.changePassword("", "").await()
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        account.getUserDetails().await()
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        account.updateUserDetails("").await()
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        account.deleteAccount().await()
    }
}