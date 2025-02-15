package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

actual object SitesApi {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    suspend fun listSites(): List<SiteResponse> {
        return SitesClient.listSitesRequest()
    }

    fun listSitesAsync(): CompletableFuture<List<SiteResponse>> {
        return completableFuture { listSites() }
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return SitesClient.getLocksForSiteRequest(siteId)
    }

    fun getLocksForSiteAsync(siteId: String): CompletableFuture<List<SiteLocksResponse>> {
        return completableFuture { getLocksForSite(siteId) }
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return SitesClient.getUsersForSiteRequest(siteId)
    }

    fun getUsersForSiteAsync(siteId: String): CompletableFuture<List<UserForSiteResponse>> {
        return completableFuture { getUsersForSite(siteId) }
    }
}

actual fun sites(): SitesApi = SitesApi