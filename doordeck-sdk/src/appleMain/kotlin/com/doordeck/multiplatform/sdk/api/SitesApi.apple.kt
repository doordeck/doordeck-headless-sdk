package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserForSiteResponse
import platform.Foundation.NSUUID

/**
 * Platform-specific implementations of sites-related API calls.
 */
actual object SitesApi {
    /**
     * @see SitesClient.listSitesRequest
     */
    @Throws(Exception::class)
    suspend fun listSites(): List<SiteResponse> = SitesClient
        .listSitesRequest()
        .toSiteResponse()

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForSite(siteId: NSUUID): List<SiteLocksResponse> = SitesClient
        .getLocksForSiteRequest(siteId.UUIDString)
        .toLockResponse()

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForSite(siteId: NSUUID): List<UserForSiteResponse> = SitesClient
        .getUsersForSiteRequest(siteId.UUIDString)
        .toUserForSiteResponse()
}

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = SitesApi