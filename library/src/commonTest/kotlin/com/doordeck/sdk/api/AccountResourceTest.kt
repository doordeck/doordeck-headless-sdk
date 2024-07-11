package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.PlatformType
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.responses.UserDetailsResponse
import com.doordeck.sdk.getPlatform
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.Crypto.decodeBase64ToByteArray
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AccountResourceTest : SystemTest() {

    @Test
    fun shouldTestAccount() = runBlocking {
        shouldGetUserDetails()
        shouldUpdateUserDetails()
        shouldRegisterEphemeralKey()
        shouldChangePassword()
    }

    private fun shouldGetUserDetails(): UserDetailsResponse {
        return ACCOUNT_RESOURCE.getUserDetails()
    }

    private fun shouldUpdateUserDetails() {
        // Given
        val updatedUserDisplayName = uuid4().toString()

        // When
        ACCOUNT_RESOURCE.updateUserDetails(updatedUserDisplayName)

        // Then
        val result = shouldGetUserDetails()
        assertEquals(updatedUserDisplayName, result.displayName)
    }

    private fun shouldRegisterEphemeralKey() = runBlocking {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runBlocking
        }

        LibsodiumInitializer.initialize()

        // Given
        val key = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()

        // When
        val result = ACCOUNT_RESOURCE.registerEphemeralKey(key)

        // Then
        assertTrue { result.certificateChain.isNotEmpty() }
        assertEquals(TEST_MAIN_USER_ID, result.userId)
    }

    private fun shouldChangePassword() {
        ACCOUNT_RESOURCE.changePassword(TEST_MAIN_USER_PASSWORD, TEST_MAIN_USER_PASSWORD)
    }
}