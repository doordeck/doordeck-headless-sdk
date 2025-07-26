package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.data.Site
import com.doordeck.multiplatform.sdk.model.data.SiteLocks
import com.doordeck.multiplatform.sdk.model.data.UserForSite
import com.doordeck.multiplatform.sdk.model.data.toLock
import com.doordeck.multiplatform.sdk.model.data.toSite
import com.doordeck.multiplatform.sdk.model.data.toUserForSite
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
    fun listSites(): Promise<List<Site>> {
        return promise { SitesClient.listSitesRequest().toSite() }
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    fun getLocksForSite(siteId: String): Promise<List<SiteLocks>> {
        return promise { SitesClient.getLocksForSiteRequest(siteId).toLock() }
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    fun getUsersForSite(siteId: String): Promise<List<UserForSite>> {
        return promise { SitesClient.getUsersForSiteRequest(siteId).toUserForSite() }
    }
}

private val sites = SitesApi

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
@JsExport
actual fun sites(): SitesApi = sites