package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.AccountResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountResourceTest : SystemTest() {

    private val resource = AccountResourceImpl(createHttpClient(TEST_ENVIRONMENT, TEST_AUTH_TOKEN, null))

    @Test
    fun shouldTestAccount() = runBlocking {
        shouldUpdateUserDetails()
    }

    private fun shouldUpdateUserDetails() {
        // Given
        val updatedUserDisplayName = uuid4().toString()

        // When
        resource.updateUserDetails(updatedUserDisplayName)

        // Then
        assertEquals(updatedUserDisplayName, resource.getUserDetails().displayName)
    }
}