package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import io.ktor.client.HttpClient

open class SitesClient(
    private val httpClient: HttpClient,
) : AbstractResourceImpl() {

    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    suspend fun listSitesRequest(): List<SiteResponse> {
        return httpClient.get(Paths.getListSites())
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    suspend fun getLocksForSiteRequest(siteId: String): List<SiteLocksResponse> {
        return httpClient.get(Paths.getLocksForSitePath(siteId))
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    suspend fun getUsersForSiteRequest(siteId: String): List<UserForSiteResponse> {
        return httpClient.get(Paths.getUsersForSitePath(siteId))
    }
}