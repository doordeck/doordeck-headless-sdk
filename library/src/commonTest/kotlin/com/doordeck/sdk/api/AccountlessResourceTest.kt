package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.AccountResourceImpl
import com.doordeck.sdk.internal.api.TokenManagerImpl
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.Jwt.getEmailFromToken
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class AccountlessResourceTest : SystemTest() {

    @Test
    fun shouldTestAccountless() = runBlocking {
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
        val response = ACCOUNTLESS_RESOURCE.registration(TEST_MAIN_USER_EMAIL.replace("@", "+${uuid4()}@"), TEST_MAIN_USER_PASSWORD)

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
        val tokenManager = TokenManagerImpl(token)
        val resource = AccountResourceImpl(createHttpClient(TEST_ENVIRONMENT, tokenManager), tokenManager)

        // When
        resource.deleteAccount()

        // Then
        assertFails {
            ACCOUNTLESS_RESOURCE.login(token.getEmailFromToken()!!, TEST_MAIN_USER_PASSWORD)
        }
    }
}