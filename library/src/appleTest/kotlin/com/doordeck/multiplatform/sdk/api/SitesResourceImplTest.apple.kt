package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesResourceImplTest {

    private val sites = SitesResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldListSites() = runTest {
        sites.listSites()
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        sites.getLocksForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        sites.getUsersForSite(DEFAULT_SITE_ID)
    }
}