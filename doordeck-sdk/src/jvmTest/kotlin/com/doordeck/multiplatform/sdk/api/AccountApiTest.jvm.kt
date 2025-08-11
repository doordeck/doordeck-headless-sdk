package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import kotlinx.coroutines.test.runTest
import java.security.KeyPair
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AccountApiTest : IntegrationTest() {

    @Test
    fun shouldGetUserDetails() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val response = AccountApi.getUserDetails()

        // Then
        assertEquals(TEST_MAIN_USER_EMAIL, response.email)
    }

    @Ignore
    @Test
    fun shouldUpdateUserDetails() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedUserDisplayName = "Training"

        // When
        AccountApi.updateUserDetails(updatedUserDisplayName)

        // Then
        val result = AccountApi.getUserDetails()
        assertEquals(updatedUserDisplayName, result.displayName)
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
        val privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY

        // When
        val result = AccountApi.registerEphemeralKey(KeyPair(publicKey, privateKey))

        // Then
        assertTrue { result.certificateChain.isNotEmpty() }
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.userId)
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, ContextManager.getUserId())
        assertEquals(result.certificateChain, ContextManager.getCertificateChain())
        assertEquals(publicKey, ContextManager.getKeyPair()?.public)
        assertEquals(privateKey, ContextManager.getKeyPair()?.private)
        assertFalse { ContextManager.isCertificateChainInvalidOrExpired() }
        assertTrue { ContextManager.isKeyPairVerified() }
    }

    @Test
    fun shouldChangePassword() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        AccountApi.changePassword(TEST_MAIN_USER_PASSWORD, TEST_MAIN_USER_PASSWORD)

        // Then
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
    }

    @Test
    fun shouldRefreshToken() = runTest {
        // Given
        val login = AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val response = AccountApi.refreshToken(login.refreshToken)

        // Then
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(ContextManager.getCloudAuthToken(), response.authToken)
        assertEquals(ContextManager.getCloudRefreshToken(), response.refreshToken)
    }

    @Test
    fun shouldLogout() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        AccountApi.logout()

        // Then
        assertNull(ContextManager.getCloudAuthToken())
        assertNull(ContextManager.getCloudRefreshToken())
        assertNull(ContextManager.getFusionAuthToken())
        assertNull(ContextManager.getUserId())
        assertNull(ContextManager.getUserEmail())
        assertNull(ContextManager.getCertificateChain())
        assertNull(ContextManager.getKeyPair())
    }
}