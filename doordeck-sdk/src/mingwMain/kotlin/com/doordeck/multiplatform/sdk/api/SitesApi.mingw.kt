package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.ResultCallback
import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.data.SiteIdData
import com.doordeck.multiplatform.sdk.util.replyAsync
import com.doordeck.multiplatform.sdk.util.fromJson

actual object SitesApi {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    fun listSites(requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        SitesClient.listSitesRequest()
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    fun getLocksForSite(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val siteIdData = data.fromJson<SiteIdData>()
        SitesClient.getLocksForSiteRequest(siteIdData.siteId)
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    fun getUsersForSite(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val siteIdData = data.fromJson<SiteIdData>()
        SitesClient.getUsersForSiteRequest(siteIdData.siteId)
    }
}

actual fun sites(): SitesApi = SitesApi