package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.LIST_SITES_RESPONSE
import com.doordeck.multiplatform.sdk.LOCKS_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.USER_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.model.GetLocksForSiteData
import com.doordeck.multiplatform.sdk.model.GetUsersForSiteData
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SitesApiTest : MockTest() {

    @Test
    fun shouldListSites() = runTest {
        val response = SitesApi.listSites()
        assertEquals(LIST_SITES_RESPONSE, response)
    }

    @Test
    fun shouldListSitesJson() = runTest {
        val response = SitesApi.listSitesJson()
        assertEquals(LIST_SITES_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        val response = SitesApi.getLocksForSite(DEFAULT_SITE_ID)
        assertEquals(LOCKS_FOR_SITE_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForSiteJson() = runTest {
        val response = SitesApi.getLocksForSiteJson(GetLocksForSiteData(DEFAULT_SITE_ID).toJson())
        assertEquals(LOCKS_FOR_SITE_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        val response = SitesApi.getUsersForSite(DEFAULT_SITE_ID)
        assertEquals(USER_FOR_SITE_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForSiteJson() = runTest {
        val response = SitesApi.getUsersForSiteJson(GetUsersForSiteData(DEFAULT_SITE_ID).toJson())
        assertEquals(USER_FOR_SITE_RESPONSE.toResultDataJson(), response)
    }
}