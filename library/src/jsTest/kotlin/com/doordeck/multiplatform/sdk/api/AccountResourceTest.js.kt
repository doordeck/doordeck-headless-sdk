package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AccountResourceTest : IntegrationTest() {

    init {
        LibsodiumInitializer.initializeWithCallback {  }
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val response = ACCOUNT_RESOURCE.getUserDetails().await()

        // Then
        assertEquals(TEST_MAIN_USER_EMAIL, response.email)
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedUserDisplayName = uuid4().toString()

        // When
        ACCOUNT_RESOURCE.updateUserDetails(updatedUserDisplayName).await()

        // Then
        val result = ACCOUNT_RESOURCE.getUserDetails().await()
        assertEquals(updatedUserDisplayName, result.displayName)
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val key = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()

        // When
        val result = ACCOUNT_RESOURCE.registerEphemeralKey(key).await()

        // Then
        assertTrue { result.certificateChain.isNotEmpty() }
        assertEquals(TEST_MAIN_USER_ID, result.userId)
    }

    @Test
    fun shouldChangePassword() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        ACCOUNT_RESOURCE.changePassword(TEST_MAIN_USER_PASSWORD, TEST_MAIN_USER_PASSWORD).await()
    }

    @Test
    fun shouldRefreshToken() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val response = ACCOUNT_RESOURCE.refreshToken(login.refreshToken).await()

        // Then
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
    }

    @Test
    fun shouldLogout() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        ACCOUNT_RESOURCE.logout().await()

        // Then
        assertNull(CONTEXT_MANAGER.currentToken)
        assertNull(CONTEXT_MANAGER.currentRefreshToken)
        assertNull(CONTEXT_MANAGER.currentFusionToken)
    }
}