package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountResourceTest : SystemTest() {

    @Test
    fun shouldTestAccount() = runBlocking {
        shouldUpdateUserDetails()
    }

    private fun shouldUpdateUserDetails() {
        // Given
        val updatedUserDisplayName = uuid4().toString()

        // When
        ACCOUNT_RESOURCE.updateUserDetails(updatedUserDisplayName)

        // Then
        val result = ACCOUNT_RESOURCE.getUserDetails()
        assertEquals(updatedUserDisplayName, result.displayName)
    }
}