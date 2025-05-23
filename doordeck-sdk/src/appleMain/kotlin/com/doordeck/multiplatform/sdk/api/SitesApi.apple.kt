package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
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
    suspend fun listSites(): List<SiteResponse> {
        return SitesClient.listSitesRequest()
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return SitesClient.getLocksForSiteRequest(siteId)
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return SitesClient.getUsersForSiteRequest(siteId)
    }
}

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = SitesApi