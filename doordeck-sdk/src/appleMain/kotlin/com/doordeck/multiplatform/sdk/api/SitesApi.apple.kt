package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse

actual object SitesApi {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun listSites(): List<SiteResponse> {
        return SitesClient.listSitesRequest()
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return SitesClient.getLocksForSiteRequest(siteId)
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return SitesClient.getUsersForSiteRequest(siteId)
    }
}

actual fun sites(): SitesApi = SitesApi