package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest : IntegrationTest() {

    @Test
    fun shouldListSites() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val sites = SITES_RESOURCE.listSites().await()

        // Then
        assertTrue { sites.isNotEmpty() }
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val locksForSite = SITES_RESOURCE.getLocksForSite(TEST_MAIN_SITE_ID).await()

        // Then
        assertTrue { locksForSite.isNotEmpty() }
        assertTrue { locksForSite.any { it.id == TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val usersForSite = SITES_RESOURCE.getUsersForSite(TEST_MAIN_SITE_ID).await()

        // Then
        assertTrue { usersForSite.isNotEmpty() }
        assertTrue { usersForSite.any { it.userId == TEST_MAIN_USER_ID } }
    }
}