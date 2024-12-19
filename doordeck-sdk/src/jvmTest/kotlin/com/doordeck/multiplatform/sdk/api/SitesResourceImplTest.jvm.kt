package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesResourceImplTest : MockTest() {

    @Test
    fun shouldListSites() = runTest {
        SitesResourceImpl.listSites()
    }

    @Test
    fun shouldListSitesAsync() = runTest {
        SitesResourceImpl.listSitesAsync().await()
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        SitesResourceImpl.getLocksForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetLocksForSiteAsync() = runTest {
        SitesResourceImpl.getLocksForSiteAsync(DEFAULT_SITE_ID).await()
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        SitesResourceImpl.getUsersForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetUsersForSiteAsync() = runTest {
        SitesResourceImpl.getUsersForSiteAsync(DEFAULT_SITE_ID).await()
    }
}