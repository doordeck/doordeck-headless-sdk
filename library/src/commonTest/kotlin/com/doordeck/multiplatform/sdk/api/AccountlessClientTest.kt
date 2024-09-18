package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.createCloudHttpClient
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class AccountlessClientTest : IntegrationTest() {

    @Test
    fun shouldLogin() = runTest {
        // When
        val response = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
    }

    @Test
    fun shouldRegisterAndDelete() = runTest {
        // Given - shouldRegister
         val newUserEmail = TEST_MAIN_USER_EMAIL.replace("@", "+${getPlatform()}-${Uuid.random()}@")

        // When
        val response = ACCOUNTLESS_CLIENT.registrationRequest(newUserEmail, TEST_MAIN_USER_PASSWORD, null, false)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }

        // Given - shouldDelete
        val contextManager = ContextManagerImpl(token = response.authToken)
        val accountClient = AccountClient(createCloudHttpClient(TEST_ENVIRONMENT, contextManager), contextManager)

        // When
        accountClient.deleteAccountRequest()

        // Then
        assertFails {
            ACCOUNTLESS_CLIENT.loginRequest(newUserEmail, TEST_MAIN_USER_PASSWORD)
        }
    }
}