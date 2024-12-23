package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountResourceImplTest : MockTest() {

    @Test
    fun shouldRefreshToken() = runTest {
        AccountResourceImpl.refreshToken("").await()
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        AccountResourceImpl.refreshToken().await()
    }

    @Test
    fun shouldLogout() = runTest {
        AccountResourceImpl.logout().await()
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        AccountResourceImpl.registerEphemeralKey(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        AccountResourceImpl.registerEphemeralKey().await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication(byteArrayOf()).await()
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        AccountResourceImpl.registerEphemeralKeyWithSecondaryAuthentication().await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistration("", TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()).await()
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        AccountResourceImpl.verifyEphemeralKeyRegistration("").await()
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        AccountResourceImpl.reverifyEmail().await()
    }

    @Test
    fun shouldChangePassword() = runTest {
        AccountResourceImpl.changePassword("", "").await()
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        AccountResourceImpl.getUserDetails().await()
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        AccountResourceImpl.updateUserDetails("").await()
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        AccountResourceImpl.deleteAccount().await()
    }
}