package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.getPlatform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class AccountlessClientTest : IntegrationTest() {

    @Test
    fun shouldLogin() = runTest {
        // When
        val response = AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(response.authToken, ContextManagerImpl.getAuthToken())
        assertEquals(response.refreshToken, ContextManagerImpl.getRefreshToken())
    }

    @Test
    fun shouldRegisterAndDelete() = runTest {
        // Given - shouldRegister
        val newUserEmail = TEST_MAIN_USER_EMAIL.replace("@", "+${getPlatform()}-${Uuid.random()}@")
        val keyPair = CryptoManager.generateKeyPair()

        // When
        val response = AccountlessClient.registrationRequest(newUserEmail, TEST_MAIN_USER_PASSWORD, null, false, keyPair.public)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(response.authToken, ContextManagerImpl.getAuthToken())
        assertEquals(response.refreshToken, ContextManagerImpl.getRefreshToken())

        // Given - shouldDelete
        // When
        AccountClient.deleteAccountRequest()

        // Then
        assertNull(ContextManagerImpl.getAuthToken())
        assertNull(ContextManagerImpl.getRefreshToken())
        assertFails {
            AccountlessClient.loginRequest(newUserEmail, TEST_MAIN_USER_PASSWORD)
        }
    }
}