package com.doordeck.sdk.api

import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.api.responses.SiteResponse
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.SitesResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest : SystemTest() {

    // Initialize the resource
    private val resource = SitesResourceImpl(createHttpClient(ApiEnvironment.DEV, TEST_AUTH_TOKEN, null))

    @Test
    fun shouldTestSites() = runBlocking {
        val sites = shouldListSites()
        shouldGetLocksForSite(sites.random().id)
        shouldGetUsersForSite(sites.random().id)
    }

    private fun shouldListSites(): Array<SiteResponse> = runBlocking {
        val sites = resource.listSites()
        assertTrue { sites.isNotEmpty() }
        return@runBlocking sites
    }

    private fun shouldGetLocksForSite(siteId: String) = runBlocking {
        val locksForSite = resource.getLocksForSite(siteId)
        assertTrue { locksForSite.isNotEmpty() }
    }

    private fun shouldGetUsersForSite(siteId: String) = runBlocking {
        val usersForSite = resource.getUsersForSite(siteId)
        assertTrue { usersForSite.isNotEmpty() }
    }
}