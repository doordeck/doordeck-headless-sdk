package com.doordeck.sdk.api

import com.doordeck.sdk.KDoordeckFactory
import com.doordeck.sdk.api.model.ApiEnvironment
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest {

    @Test
    fun shouldTestSite() {
        // Initialize the SDK
        val sdk = KDoordeckFactory().initialize(ApiEnvironment.DEV, "")

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