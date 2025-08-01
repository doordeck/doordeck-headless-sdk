package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

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
        val updatedUserDisplayName = "Training"

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
        val publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()
        val privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()

        // When
        val result = AccountClient.registerEphemeralKeyRequest(publicKey, privateKey)

        // Then
        assertTrue { result.certificateChain.isNotEmpty() }
        assertEquals(TEST_MAIN_USER_ID, result.userId)
        assertEquals(result.userId, ContextManagerImpl.getUserId())
        assertEquals(result.certificateChain, ContextManagerImpl.getCertificateChain())
        assertContentEquals(publicKey, ContextManagerImpl.getPublicKey())
        assertContentEquals(privateKey, ContextManagerImpl.getPrivateKey())
        assertFalse { ContextManagerImpl.isCertificateChainInvalidOrExpired() }
        assertTrue { ContextManagerImpl.isKeyPairVerified() }
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
        assertEquals(ContextManagerImpl.getCloudAuthToken(), response.authToken)
        assertEquals(ContextManagerImpl.getCloudRefreshToken(), response.refreshToken)
    }

    @Test
    fun shouldLogout() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        AccountClient.logoutRequest()

        // Then
        assertNull(ContextManagerImpl.getCloudAuthToken())
        assertNull(ContextManagerImpl.getCloudRefreshToken())
        assertNull(ContextManagerImpl.getFusionAuthToken())
    }
}