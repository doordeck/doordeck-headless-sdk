package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.SystemTest
import com.doordeck.multiplatform.sdk.createCloudHttpClient
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.util.Jwt.getEmailFromToken
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class AccountlessResourceTest : SystemTest() {

    @Test
    fun shouldTestAccountless() = runTest {
        shouldLogin()
        val token = shouldRegister()
        shouldDelete(token)
    }

    private fun shouldLogin() {
        // When
        val response = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
    }

    private fun shouldRegister(): String {
        // When
        val response = ACCOUNTLESS_RESOURCE.registration(TEST_MAIN_USER_EMAIL.replace("@", "+${getPlatform()}-${uuid4()}@"), TEST_MAIN_USER_PASSWORD)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }

        return response.authToken
    }

    /**
     * In this test, we are calling a different resource. Since we want to delete the user that we recently created,
     * we need to create the resource with the right token instead of using the one used in the tests
     */
    private fun shouldDelete(token: String) {
        // Given
        val contextManager = ContextManagerImpl(token)
        val resource = AccountResourceImpl(createCloudHttpClient(TEST_ENVIRONMENT, contextManager), contextManager)

        // When
        resource.deleteAccount()

        // Then
        assertFails {
            ACCOUNTLESS_RESOURCE.login(token.getEmailFromToken()!!, TEST_MAIN_USER_PASSWORD)
        }
    }
}