package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.LIST_SITES_RESPONSE
import com.doordeck.multiplatform.sdk.LOCKS_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.USER_FOR_SITE_RESPONSE
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SitesApiTest : MockTest() {

    @Test
    fun shouldListSites() = runTest {
        val response = SitesApi.listSites().await()
        assertEquals(LIST_SITES_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        val response = SitesApi.getLocksForSite(DEFAULT_SITE_ID).await()
        assertEquals(LOCKS_FOR_SITE_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        val response = SitesApi.getUsersForSite(DEFAULT_SITE_ID).await()
        assertEquals(USER_FOR_SITE_RESPONSE, response)
    }
}