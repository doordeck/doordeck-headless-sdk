package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesResourceImplTest {

    private val sites = SitesResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldListSites() = runTest {
        sites.listSites()
    }

    @Test
    fun shouldListSitesFuture() = runTest {
        sites.listSitesFuture().await()
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        sites.getLocksForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetLocksForSiteFuture() = runTest {
        sites.getLocksForSiteFuture(DEFAULT_SITE_ID).await()
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        sites.getUsersForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetUsersForSiteFuture() = runTest {
        sites.getUsersForSiteFuture(DEFAULT_SITE_ID).await()
    }
}