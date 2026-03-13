package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesApiAsyncTest : IntegrationTest() {

    @Test
    fun shouldListSitesAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val sites = SitesApi.listSitesAsync().await()

        // Then
        assertTrue { sites.isNotEmpty() }
        assertTrue { sites.any { it.id == PLATFORM_TEST_MAIN_SITE_ID } }
    }

    @Test
    fun shouldGetLocksForSiteAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val locksForSite = SitesApi.getLocksForSiteAsync(PLATFORM_TEST_MAIN_SITE_ID).await()

        // Then
        assertTrue { locksForSite.isNotEmpty() }
        assertTrue { locksForSite.any { it.id == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldGetUsersForSiteAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val usersForSite = SitesApi.getUsersForSiteAsync(PLATFORM_TEST_MAIN_SITE_ID).await()

        // Then
        assertTrue { usersForSite.isNotEmpty() }
        assertTrue { usersForSite.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }
    }
}