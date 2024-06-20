package com.doordeck.sdk.api

import com.doordeck.sdk.KDoordeckFactory
import com.doordeck.sdk.api.model.ApiEnvironment
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest {

    @Test
    fun shouldTestSite() {
        val sdk = KDoordeckFactory().initialize(ApiEnvironment.DEV, "")

        val sites = sdk.sites().listSites()
        assertTrue { sites.isNotEmpty() }

        val locksForSite = sdk.sites().getLocksForSite(sites.random().id)
        assertTrue { locksForSite.isNotEmpty() }

        val usersForSite = sdk.sites().getUsersForSite(sites.random().id)
        assertTrue { usersForSite.isNotEmpty() }
    }
}