package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.TEST_MOCK_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        CloudHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
    }

    @Test
    fun shouldListSites() = runTest {
        SitesResourceImpl.listSites().await()
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        SitesResourceImpl.getLocksForSite(DEFAULT_SITE_ID).await()
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        SitesResourceImpl.getUsersForSite(DEFAULT_SITE_ID).await()
    }
}