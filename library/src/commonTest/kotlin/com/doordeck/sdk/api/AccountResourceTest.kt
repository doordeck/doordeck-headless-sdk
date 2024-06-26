package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.AccountResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountResourceTest : SystemTest() {

    private val updatedUserDisplayName = uuid4().toString()

    @Test
    fun shouldTestAccount() = runBlocking {
        // Should initialize the resource
        val resource = AccountResourceImpl(createHttpClient(ApiEnvironment.DEV, TEST_AUTH_TOKEN, null))

        // Update user details
        resource.updateUserDetails(updatedUserDisplayName)

        // Retrieve the user details
        val user = resource.getUserDetails()
        assertEquals(updatedUserDisplayName, user.displayName)
    }
}