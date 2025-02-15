package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

@JsExport
actual object SitesApi {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    fun listSites(): Promise<List<SiteResponse>> {
        return promise { SitesClient.listSitesRequest() }
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    fun getLocksForSite(siteId: String): Promise<List<SiteLocksResponse>> {
        return promise { SitesClient.getLocksForSiteRequest(siteId) }
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    fun getUsersForSite(siteId: String): Promise<List<UserForSiteResponse>> {
        return promise { SitesClient.getUsersForSiteRequest(siteId) }
    }
}

private val sites = SitesApi

@JsExport
actual fun sites(): SitesApi = sites