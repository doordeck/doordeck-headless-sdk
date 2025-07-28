package com.doordeck.multiplatform.sdk.clients
/*
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.platformType
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
        assertEquals(response.authToken, Context.getCloudAuthToken())
        assertEquals(response.refreshToken, Context.getCloudRefreshToken())
    }

    @Test
    fun shouldRegisterAndDelete() = runTest {
        // Given - shouldRegister
        val newUserEmail = TEST_MAIN_USER_EMAIL.replace("@", "+$platformType-${Uuid.random()}@")
        val keyPair = CryptoManager.generateRawKeyPair()

        // When
        val response = AccountlessClient.registrationRequest(newUserEmail, TEST_MAIN_USER_PASSWORD, null, false, keyPair.public)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
        assertEquals(response.authToken, Context.getCloudAuthToken())
        assertEquals(response.refreshToken, Context.getCloudRefreshToken())

        // Given - shouldDelete
        // When
        AccountClient.deleteAccountRequest()

        // Then
        assertNull(Context.getCloudAuthToken())
        assertNull(Context.getCloudRefreshToken())
        assertFails {
            AccountlessClient.loginRequest(newUserEmail, TEST_MAIN_USER_PASSWORD)
        }
    }
}
 */