package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class AccountClientTest : IntegrationTest() {

    @Test
    fun shouldGetUserDetails() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val response = AccountClient.getUserDetailsRequest()

        // Then
        assertEquals(TEST_MAIN_USER_EMAIL, response.email)
    }

    @Ignore
    @Test
    fun shouldUpdateUserDetails() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedUserDisplayName = Uuid.random().toString()

        // When
        AccountClient.updateUserDetailsRequest(updatedUserDisplayName)

        // Then
        val result = AccountClient.getUserDetailsRequest()
        assertEquals(updatedUserDisplayName, result.displayName)
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val key = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()

        // When
        val result = AccountClient.registerEphemeralKeyRequest(key)

        // Then
        assertTrue { result.certificateChain.isNotEmpty() }
        assertEquals(TEST_MAIN_USER_ID, result.userId)
    }

    @Test
    fun shouldChangePassword() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        AccountClient.changePasswordRequest(TEST_MAIN_USER_PASSWORD, TEST_MAIN_USER_PASSWORD)

        // Then
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
    }

    @Test
    fun shouldRefreshToken() = runTest {
        // Given
        val login = AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val response = AccountClient.refreshTokenRequest(login.refreshToken)

        // Then
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(ContextManagerImpl.getAuthToken(), response.authToken)
        assertEquals(ContextManagerImpl.getRefreshToken(), response.refreshToken)
    }

    @Test
    fun shouldLogout() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        AccountClient.logoutRequest()

        // Then
        assertNull(ContextManagerImpl.getAuthToken())
        assertNull(ContextManagerImpl.getRefreshToken())
        assertNull(ContextManagerImpl.getFusionAuthToken())
    }
}