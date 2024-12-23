package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesResourceImplTest : MockTest() {

    @Test
    fun shouldListSites() = runTest {
        SitesResourceImpl.listSites()
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        SitesResourceImpl.getLocksForSite(DEFAULT_SITE_ID)
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        SitesResourceImpl.getUsersForSite(DEFAULT_SITE_ID)
    }
}