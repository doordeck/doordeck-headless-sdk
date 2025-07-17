package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.LIST_SITES_RESPONSE
import com.doordeck.multiplatform.sdk.LOCKS_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.USER_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.util.toUUID
import kotlinx.coroutines.future.await
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
    fun shouldListSitesAsync() = runTest {
        val response = SitesApi.listSitesAsync().await()
        assertEquals(LIST_SITES_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        val response = SitesApi.getLocksForSite(DEFAULT_SITE_ID.toUUID())
        assertEquals(LOCKS_FOR_SITE_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForSiteAsync() = runTest {
        val response = SitesApi.getLocksForSiteAsync(DEFAULT_SITE_ID.toUUID()).await()
        assertEquals(LOCKS_FOR_SITE_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        val response = SitesApi.getUsersForSite(DEFAULT_SITE_ID.toUUID())
        assertEquals(USER_FOR_SITE_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForSiteAsync() = runTest {
        val response = SitesApi.getUsersForSiteAsync(DEFAULT_SITE_ID.toUUID()).await()
        assertEquals(USER_FOR_SITE_RESPONSE, response)
    }
}