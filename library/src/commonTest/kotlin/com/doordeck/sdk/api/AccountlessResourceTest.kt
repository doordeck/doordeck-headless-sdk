package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class AccountlessResourceTest : SystemTest() {

    @Test
    fun shouldTestAccountless() = runBlocking {
        shouldLogin()
        shouldRegister()
    }

    private fun shouldLogin() {
        // When
        val response = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
    }

    private fun shouldRegister() {
        // When
        val response = ACCOUNTLESS_RESOURCE.registration(TEST_MAIN_USER_EMAIL.replace("@", "+${uuid4()}@"), TEST_MAIN_USER_PASSWORD)

        // When
        assertTrue { response.authToken.isNotEmpty() }
        assertTrue { response.refreshToken.isNotEmpty() }
    }
}