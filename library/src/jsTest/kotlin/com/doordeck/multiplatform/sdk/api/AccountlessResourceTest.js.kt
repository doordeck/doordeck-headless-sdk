package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.createCloudHttpClient
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class AccountlessResourceTest : IntegrationTest() {

    @Test
    fun shouldLogin() = runTest {
        // When
        val response = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
    }

    @Test
    fun shouldRegisterAndDelete() = runTest {
        // Given - shouldRegister
         val newUserEmail = TEST_MAIN_USER_EMAIL.replace("@", "+${getPlatform()}-${uuid4()}@")

        // When
        val response = ACCOUNTLESS_RESOURCE.registration(newUserEmail, TEST_MAIN_USER_PASSWORD).await()

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }

        // Given - shouldDelete
        val contextManager = ContextManagerImpl(response.authToken)
        val resource = AccountResourceImpl(createCloudHttpClient(TEST_ENVIRONMENT, contextManager), contextManager)

        // When
        resource.deleteAccount().await()

        // Then
        assertFails {
            ACCOUNTLESS_RESOURCE.login(newUserEmail, TEST_MAIN_USER_PASSWORD).await()
        }
    }
}