package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.TEST_CLOUD_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.SitesClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesClientTest {


    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        CloudHttpClient.overrideClient(TEST_CLOUD_CLIENT)
    }

    @Test
    fun shouldListSites() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val sites = SitesClient.listSitesRequest()

        // Then
        assertTrue { sites.isNotEmpty() }
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val locksForSite = SitesClient.getLocksForSiteRequest(TEST_MAIN_SITE_ID)

        // Then
        assertTrue { locksForSite.isNotEmpty() }
        assertTrue { locksForSite.any { it.id == TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val usersForSite = SitesClient.getUsersForSiteRequest(TEST_MAIN_SITE_ID)

        // Then
        assertTrue { usersForSite.isNotEmpty() }
        assertTrue { usersForSite.any { it.userId == TEST_MAIN_USER_ID } }
    }
}