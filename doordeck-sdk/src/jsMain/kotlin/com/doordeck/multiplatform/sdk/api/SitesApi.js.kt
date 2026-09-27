package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserForSiteResponse
import kotlin.js.collections.JsArray

/**
 * Platform-specific implementations of sites-related API calls.
 */
@JsExport
actual object SitesApi {
    /**
     * @see SitesClient.listSitesRequest
     */
    suspend fun listSites(): JsArray<SiteResponse> = SitesClient
        .listSitesRequest()
        .toSiteResponse()

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    suspend fun getLocksForSite(siteId: String): JsArray<SiteLocksResponse> = SitesClient
        .getLocksForSiteRequest(siteId)
        .toLockResponse()

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    suspend fun getUsersForSite(siteId: String): JsArray<UserForSiteResponse> = SitesClient
        .getUsersForSiteRequest(siteId)
        .toUserForSiteResponse()
}

private val sites = SitesApi

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = sites