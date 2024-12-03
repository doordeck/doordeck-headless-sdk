package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.api.model.GetLocksForSiteData
import com.doordeck.multiplatform.sdk.api.model.GetUsersForSiteData
import com.doordeck.multiplatform.sdk.internal.api.SitesClient
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesResourceImplTest {

    private val sites = SitesResourceImpl(SitesClient(TEST_HTTP_CLIENT))

    @Test
    fun shouldListSites() = runTest {
        sites.listSites()
    }

    @Test
    fun shouldListSitesJson() = runTest {
        sites.listSitesJson()
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        sites.getLocksForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetLocksForSiteJson() = runTest {
        sites.getLocksForSiteJson(GetLocksForSiteData(DEFAULT_SITE_ID).toJson())
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        sites.getUsersForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetUsersForSiteJson() = runTest {
        sites.getUsersForSiteJson(GetUsersForSiteData(DEFAULT_SITE_ID).toJson())
    }
}