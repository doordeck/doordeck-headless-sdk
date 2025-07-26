package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.data.Site
import com.doordeck.multiplatform.sdk.model.data.SiteLocks
import com.doordeck.multiplatform.sdk.model.data.UserForSite
import com.doordeck.multiplatform.sdk.model.data.toLock
import com.doordeck.multiplatform.sdk.model.data.toSite
import com.doordeck.multiplatform.sdk.model.data.toUserForSite
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse

/**
 * Platform-specific implementations of sites-related API calls.
 */
actual object SitesApi {
    /**
     * @see SitesClient.listSitesRequest
     */
    @Throws(Exception::class)
    suspend fun listSites(): List<Site> {
        return SitesClient.listSitesRequest()
            .toSite()
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForSite(siteId: String): List<SiteLocks> {
        return SitesClient.getLocksForSiteRequest(siteId)
            .toLock()
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForSite(siteId: String): List<UserForSite> {
        return SitesClient.getUsersForSiteRequest(siteId)
            .toUserForSite()
    }
}

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = SitesApi