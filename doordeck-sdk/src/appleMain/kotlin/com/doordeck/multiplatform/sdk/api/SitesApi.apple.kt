package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.NetworkSiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUserForSiteResponse

/**
 * Platform-specific implementations of sites-related API calls.
 */
actual object SitesApi {
    /**
     * @see SitesClient.listSitesRequest
     */
    @Throws(Exception::class)
    suspend fun listSites(): List<NetworkSiteResponse> {
        return SitesClient.listSitesRequest()
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForSite(siteId: String): List<NetworkSiteLocksResponse> {
        return SitesClient.getLocksForSiteRequest(siteId)
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForSite(siteId: String): List<NetworkUserForSiteResponse> {
        return SitesClient.getUsersForSiteRequest(siteId)
    }
}

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = SitesApi