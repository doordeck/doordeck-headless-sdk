package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.SystemTest
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest : SystemTest() {

    @Test
    fun shouldTestSites() = runBlocking {
        val sites = shouldListSites()
        shouldGetLocksForSite(sites.random().id)
        shouldGetUsersForSite(sites.random().id)
    }

    private fun shouldListSites(): Array<SiteResponse> {
        // When
        val sites = SITES_RESOURCE.listSites()

        // Then
        assertTrue { sites.isNotEmpty() }
        return sites
    }

    private fun shouldGetLocksForSite(siteId: String) {
        // When
        val locksForSite = SITES_RESOURCE.getLocksForSite(siteId)

        // Then
        assertTrue { locksForSite.isNotEmpty() }
    }

    private fun shouldGetUsersForSite(siteId: String) {
        // When
        val usersForSite = SITES_RESOURCE.getUsersForSite(siteId)

        // Then
        assertTrue { usersForSite.isNotEmpty() }
    }
}