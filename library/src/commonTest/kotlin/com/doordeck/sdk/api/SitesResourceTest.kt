package com.doordeck.sdk.api

import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.SitesResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest : SystemTest() {

    @Test
    fun shouldTestSite() = runBlocking {
        // Initialize the resource
        val resource = SitesResourceImpl(createHttpClient(ApiEnvironment.DEV, TEST_MAIN_APPLICATION_OWNER, null))

        // Retrieve the sites
        val sites = resource.listSites()
        assertTrue { sites.isNotEmpty() }

        // Retrieve the locks for a site
        val locksForSite = resource.getLocksForSite(sites.random().id)
        assertTrue { locksForSite.isNotEmpty() }

        // Retrieve the users for a site
        val usersForSite = resource.getUsersForSite(sites.random().id)
        assertTrue { usersForSite.isNotEmpty() }
    }
}