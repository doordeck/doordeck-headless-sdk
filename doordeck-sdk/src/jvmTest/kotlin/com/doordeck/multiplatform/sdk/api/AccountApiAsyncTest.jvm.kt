package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import java.security.KeyPair
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AccountApiAsyncTest : IntegrationTest() {

    @Test
    fun shouldGetUserDetailsAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val response = AccountApi.getUserDetailsAsync().await()

        // Then
        assertEquals(TEST_MAIN_USER_EMAIL, response.email)
    }

    @Ignore
    @Test
    fun shouldUpdateUserDetailsAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedUserDisplayName = "Training"

        // When
        AccountApi.updateUserDetailsAsync(updatedUserDisplayName).await()

        // Then
        val result = AccountApi.getUserDetailsAsync().await()
        assertEquals(updatedUserDisplayName, result.displayName)
    }

    @Test
    fun shouldRegisterEphemeralKeyAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
        val privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY

        // When
        val result = AccountApi.registerEphemeralKeyAsync(KeyPair(publicKey, privateKey)).await()

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
    fun shouldChangePasswordAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        AccountApi.changePasswordAsync(TEST_MAIN_USER_PASSWORD, TEST_MAIN_USER_PASSWORD).await()

        // Then
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
    }

    @Test
    fun shouldRefreshTokenAsync() = runTest {
        // Given
        val login = AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val response = AccountApi.refreshTokenAsync(login.refreshToken).await()

        // Then
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(ContextManager.getCloudAuthToken(), response.authToken)
        assertEquals(ContextManager.getCloudRefreshToken(), response.refreshToken)
    }

    @Test
    fun shouldLogoutAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        AccountApi.logoutAsync().await()

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