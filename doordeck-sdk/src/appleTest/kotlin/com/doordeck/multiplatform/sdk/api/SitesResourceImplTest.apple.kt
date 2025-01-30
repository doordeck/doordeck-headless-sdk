package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.LIST_SITES_RESPONSE
import com.doordeck.multiplatform.sdk.LOCKS_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.USER_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SitesResourceImplTest : MockTest() {

    @Test
    fun shouldListSites() = runTest {
        val response = SitesResourceImpl.listSites()
        assertEquals(LIST_SITES_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        val response = SitesResourceImpl.getLocksForSite(DEFAULT_SITE_ID)
        assertEquals(LOCKS_FOR_SITE_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        val response = SitesResourceImpl.getUsersForSite(DEFAULT_SITE_ID)
        assertEquals(USER_FOR_SITE_RESPONSE, response)
    }
}