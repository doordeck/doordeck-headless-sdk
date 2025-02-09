package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

internal object SitesClient {

    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    suspend fun listSitesRequest(): List<SiteResponse> {
        return CloudHttpClient.client.get(Paths.getListSites()).body()
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    suspend fun getLocksForSiteRequest(siteId: String): List<SiteLocksResponse> {
        return CloudHttpClient.client.get(Paths.getLocksForSitePath(siteId)).body()
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    suspend fun getUsersForSiteRequest(siteId: String): List<UserForSiteResponse> {
        return CloudHttpClient.client.get(Paths.getUsersForSitePath(siteId)).body()
    }
}