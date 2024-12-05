package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val account = AccountResourceImpl(AccountClient(TEST_HTTP_CLIENT, contextManager))

    init {
        LibsodiumInitializer.initializeWithCallback {  }
        contextManager.setKeyPair(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
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
    fun shouldRegisterEphemeralKeyWithContext() = runTest {
        account.registerEphemeralKeyWithContext().await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationWithContext() = runTest {
        account.registerEphemeralKeyWithSecondaryAuthenticationWithContext().await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        account.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()).await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationWithContext() = runTest {
        account.verifyEphemeralKeyRegistrationWithContext("").await()
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