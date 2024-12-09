package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.TEST_MOCK_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.api.model.GetLocksForSiteData
import com.doordeck.multiplatform.sdk.api.model.GetUsersForSiteData
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        CloudHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
    }

    @Test
    fun shouldListSites() = runTest {
        SitesResourceImpl.listSites()
    }

    @Test
    fun shouldListSitesJson() = runTest {
        SitesResourceImpl.listSitesJson()
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        SitesResourceImpl.getLocksForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetLocksForSiteJson() = runTest {
        SitesResourceImpl.getLocksForSiteJson(GetLocksForSiteData(DEFAULT_SITE_ID).toJson())
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        SitesResourceImpl.getUsersForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetUsersForSiteJson() = runTest {
        SitesResourceImpl.getUsersForSiteJson(GetUsersForSiteData(DEFAULT_SITE_ID).toJson())
    }
}