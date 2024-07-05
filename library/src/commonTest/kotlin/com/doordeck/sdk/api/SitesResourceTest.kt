package com.doordeck.sdk.api

import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.responses.SiteResponse
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.SitesResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest : SystemTest() {

    private val resource = SitesResourceImpl(createHttpClient(TEST_ENVIRONMENT, TEST_AUTH_TOKEN, null))

    @Test
    fun shouldTestSites() = runBlocking {
        val sites = shouldListSites()
        shouldGetLocksForSite(sites.random().id)
        shouldGetUsersForSite(sites.random().id)
    }

    private fun shouldListSites(): Array<SiteResponse> {
        // When
        val sites = resource.listSites()

        // Then
        assertTrue { sites.isNotEmpty() }
        return sites
    }

    private fun shouldGetLocksForSite(siteId: String) {
        // When
        val locksForSite = resource.getLocksForSite(siteId)

        // Then
        assertTrue { locksForSite.isNotEmpty() }
    }

    private fun shouldGetUsersForSite(siteId: String) {
        // When
        val usersForSite = resource.getUsersForSite(siteId)

        // Then
        assertTrue { usersForSite.isNotEmpty() }
    }
}