package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.internal.api.SitesClient
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesResourceImplTest {

    private val sites = SitesResourceImpl(SitesClient(TEST_HTTP_CLIENT))

    @Test
    fun shouldListSites() = runTest {
        sites.listSites().await()
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        sites.getLocksForSite(DEFAULT_SITE_ID).await()
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        sites.getUsersForSite(DEFAULT_SITE_ID).await()
    }
}