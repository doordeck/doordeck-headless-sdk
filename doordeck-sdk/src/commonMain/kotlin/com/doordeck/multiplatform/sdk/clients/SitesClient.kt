package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.responses.BasicSiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserForSiteResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Internal implementation of the sites API client.
 * Handles all  requests related to sites.
 */
internal object SitesClient {
    /**
     * Retrieves all sites accessible to the current user.
     *
     * @return List of [BasicSiteResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/sites/list-sites">API Doc</a>
     */
    suspend fun listSitesRequest(): List<BasicSiteResponse> {
        return CloudHttpClient.client.get(Paths.getListSites()).body()
    }

    /**
     * Retrieves all user-accessible locks in the specified site.
     *
     * @param siteId The site's unique identifier.
     * @return List of [BasicSiteLocksResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/sites/get-locks-for-site">API Doc</a>
     */
    suspend fun getLocksForSiteRequest(siteId: String): List<BasicSiteLocksResponse> {
        return CloudHttpClient.client.get(Paths.getLocksForSitePath(siteId)).body()
    }

    /**
     * Retrieves users for the specified site, filtered to only include locks
     * where the current user has administrator privileges.
     *
     * @param siteId The site's unique identifier.
     * @return List of [BasicUserForSiteResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/sites/get-users-for-a-site">API Doc</a>
     */
    suspend fun getUsersForSiteRequest(siteId: String): List<BasicUserForSiteResponse> {
        return CloudHttpClient.client.get(Paths.getUsersForSitePath(siteId)).body()
    }
}