package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CStringCallback
import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.data.SiteIdData
import com.doordeck.multiplatform.sdk.util.handleCallback
import com.doordeck.multiplatform.sdk.util.fromJson

actual object SitesApi {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    @CName("listSites")
    fun listSites(callback: CStringCallback) = callback.handleCallback {
        SitesClient.listSitesRequest()
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    @CName("getLocksForSite")
    fun getLocksForSite(data: String, callback: CStringCallback) = callback.handleCallback {
        val siteIdData = data.fromJson<SiteIdData>()
        SitesClient.getLocksForSiteRequest(siteIdData.siteId)
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    @CName("getUsersForSite")
    fun getUsersForSite(data: String, callback: CStringCallback) = callback.handleCallback {
        val siteIdData = data.fromJson<SiteIdData>()
        SitesClient.getUsersForSiteRequest(siteIdData.siteId)
    }
}

actual fun sites(): SitesApi = SitesApi