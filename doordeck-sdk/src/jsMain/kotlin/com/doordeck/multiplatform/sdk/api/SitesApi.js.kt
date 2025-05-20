package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

/**
 * Platform-specific implementations of sites-related API calls.
 */
@JsExport
actual object SitesApi {
    /**
     * @see SitesClient.listSitesRequest
     */
    fun listSites(): Promise<List<SiteResponse>> {
        return promise { SitesClient.listSitesRequest() }
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    fun getLocksForSite(siteId: String): Promise<List<SiteLocksResponse>> {
        return promise { SitesClient.getLocksForSiteRequest(siteId) }
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    fun getUsersForSite(siteId: String): Promise<List<UserForSiteResponse>> {
        return promise { SitesClient.getUsersForSiteRequest(siteId) }
    }
}

private val sites = SitesApi

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
@JsExport
actual fun sites(): SitesApi = sites