package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.toLockResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserForSiteResponse

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
            .toSiteResponse()
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return SitesClient.getLocksForSiteRequest(siteId)
            .toLockResponse()
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return SitesClient.getUsersForSiteRequest(siteId)
            .toUserForSiteResponse()
    }
}

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = SitesApi