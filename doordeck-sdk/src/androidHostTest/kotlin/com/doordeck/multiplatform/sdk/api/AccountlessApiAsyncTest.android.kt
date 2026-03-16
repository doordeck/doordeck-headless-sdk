package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomUuidString
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AccountlessApiAsyncTest : IntegrationTest() {

    @Test
    fun shouldLoginAsync() = runTest {
        // When
        val response = AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(response.authToken, ContextManager.getCloudAuthToken())
        assertEquals(response.refreshToken, ContextManager.getCloudRefreshToken())
        assertEquals(TEST_MAIN_USER_EMAIL, ContextManager.getUserEmail())
    }

    @Test
    fun shouldRegisterAndDeleteAsync() = runTest {
        // Given - shouldRegister
        val newUserEmail = TEST_MAIN_USER_EMAIL.replace("@", "+$platformType-${randomUuidString()}@")
        val keyPair = CryptoManager.generateKeyPair()

        // When
        val response = AccountlessApi.registrationAsync(newUserEmail, TEST_MAIN_USER_PASSWORD, null, false, keyPair.public).await()

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(response.authToken, ContextManager.getCloudAuthToken())
        assertEquals(response.refreshToken, ContextManager.getCloudRefreshToken())
        assertEquals(newUserEmail, ContextManager.getUserEmail())

        // Given - shouldDelete
        // When
        AccountApi.deleteAccountAsync().await()

        // Then
        assertNull(ContextManager.getCloudAuthToken())
        assertNull(ContextManager.getCloudRefreshToken())
        assertNull(ContextManager.getFusionAuthToken())
        assertNull(ContextManager.getUserId())
        assertNull(ContextManager.getUserEmail())
        assertNull(ContextManager.getCertificateChain())
        assertNull(ContextManager.getKeyPair())
        assertFails {
            AccountlessApi.loginAsync(newUserEmail, TEST_MAIN_USER_PASSWORD).await()
        }
    }
}