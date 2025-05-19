package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Internal implementation of the sites API client.
 * Handles all network requests related to sites.
 */
internal object SitesClient {
    /**
     * Retrieves all sites accessible to the current user.
     *
     * @return List of [SiteResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    suspend fun listSitesRequest(): List<SiteResponse> {
        return CloudHttpClient.client.get(Paths.getListSites()).body()
    }

    /**
     * Retrieves all user-accessible locks in the specified site.
     *
     * @param siteId The site ID.
     * @return List of [SiteLocksResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    suspend fun getLocksForSiteRequest(siteId: String): List<SiteLocksResponse> {
        return CloudHttpClient.client.get(Paths.getLocksForSitePath(siteId)).body()
    }

    /**
     * Retrieves users for the specified site, filtered to only include locks
     * where the current user has administrator privileges.
     *
     * @param siteId The site ID.
     * @return List of [UserForSiteResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    suspend fun getUsersForSiteRequest(siteId: String): List<UserForSiteResponse> {
        return CloudHttpClient.client.get(Paths.getUsersForSitePath(siteId)).body()
    }
}