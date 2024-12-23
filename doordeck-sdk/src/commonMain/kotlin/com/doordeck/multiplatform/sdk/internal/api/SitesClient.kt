package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse

internal object SitesClient : AbstractResourceImpl() {

    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    suspend fun listSitesRequest(): List<SiteResponse> {
        return CloudHttpClient.get(Paths.getListSites())
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    suspend fun getLocksForSiteRequest(siteId: String): List<SiteLocksResponse> {
        return CloudHttpClient.get(Paths.getLocksForSitePath(siteId))
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    suspend fun getUsersForSiteRequest(siteId: String): List<UserForSiteResponse> {
        return CloudHttpClient.get(Paths.getUsersForSitePath(siteId))
    }
}