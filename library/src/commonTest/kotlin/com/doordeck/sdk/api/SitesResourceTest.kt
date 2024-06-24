package com.doordeck.sdk.api

import com.doordeck.sdk.SystemTest
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest : SystemTest() {

    @Test
    fun shouldTestSite() {
        // Retrieve the sites
        val sites = sdk.sites().listSites()
        assertTrue { sites.isNotEmpty() }

        // Retrieve the locks for a site
        val locksForSite = sdk.sites().getLocksForSite(sites.random().id)
        assertTrue { locksForSite.isNotEmpty() }

        // Retrieve the users for a site
        val usersForSite = sdk.sites().getUsersForSite(sites.random().id)
        assertTrue { usersForSite.isNotEmpty() }
    }
}