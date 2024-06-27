package com.doordeck.sdk.api

import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.SitesResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class SitesResourceTest : SystemTest() {

    // Initialize the resource
    private val resource = SitesResourceImpl(createHttpClient(ApiEnvironment.DEV, TEST_AUTH_TOKEN, null))

    @Test
    fun shouldListSites() = runBlocking {
        val sites = resource.listSites()
        assertTrue { sites.isNotEmpty() }
    }

    @Test
    fun shouldGetLocksForSite() = runBlocking {
        val locksForSite = resource.getLocksForSite(resource.listSites().random().id)
        assertTrue { locksForSite.isNotEmpty() }
    }

    @Test
    fun shouldGetUsersForSite() = runBlocking {
        val usersForSite = resource.getUsersForSite(resource.listSites().random().id)
        assertTrue { usersForSite.isNotEmpty() }
    }
}