package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.platformType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class AccountlessApiTest : IntegrationTest() {

    @Test
    fun shouldLogin() = runTest {
        // When
        val response = AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(response.authToken, ContextManager.getCloudAuthToken())
        assertEquals(response.refreshToken, ContextManager.getCloudRefreshToken())
        assertEquals(TEST_MAIN_USER_EMAIL, ContextManager.getUserEmail())
    }

    @Test
    fun shouldRegisterAndDelete() = runTest {
        // Given - shouldRegister
        val newUserEmail = TEST_MAIN_USER_EMAIL.replace("@", "+$platformType-${Uuid.random()}@")
        val keyPair = CryptoManager.generateKeyPair()

        // When
        val response = AccountlessApi.registration(newUserEmail, TEST_MAIN_USER_PASSWORD, null, false, keyPair.public)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(response.authToken, ContextManager.getCloudAuthToken())
        assertEquals(response.refreshToken, ContextManager.getCloudRefreshToken())
        assertEquals(newUserEmail, ContextManager.getUserEmail())

        // Given - shouldDelete
        // When
        AccountApi.deleteAccount()

        // Then
        assertNull(ContextManager.getCloudAuthToken())
        assertNull(ContextManager.getCloudRefreshToken())
        assertNull(ContextManager.getFusionAuthToken())
        assertNull(ContextManager.getUserId())
        assertNull(ContextManager.getUserEmail())
        assertNull(ContextManager.getCertificateChain())
        assertNull(ContextManager.getKeyPair())
        assertFails {
            AccountlessApi.login(newUserEmail, TEST_MAIN_USER_PASSWORD)
        }
    }
}