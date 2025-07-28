package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toLock
import com.doordeck.multiplatform.sdk.model.responses.toSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserForSiteResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of sites-related API calls.
 */
actual object SitesApi {
    /**
     * @see SitesClient.listSitesRequest
     */
    suspend fun listSites(): List<SiteResponse> {
        return SitesClient.listSitesRequest()
            .toSiteResponse()
    }

    /**
     * Async variant of [SitesApi.listSites] returning [CompletableFuture].
     */
    fun listSitesAsync(): CompletableFuture<List<SiteResponse>> {
        return completableFuture { listSites() }
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return SitesClient.getLocksForSiteRequest(siteId)
            .toLock()
    }

    /**
     * Async variant of [SitesApi.getLocksForSite] returning [CompletableFuture].
     */
    fun getLocksForSiteAsync(siteId: String): CompletableFuture<List<SiteLocksResponse>> {
        return completableFuture { getLocksForSite(siteId) }
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return SitesClient.getUsersForSiteRequest(siteId)
            .toUserForSiteResponse()
    }

    /**
     * Async variant of [SitesApi.getUsersForSite] returning [CompletableFuture].
     */
    fun getUsersForSiteAsync(siteId: String): CompletableFuture<List<UserForSiteResponse>> {
        return completableFuture { getUsersForSite(siteId) }
    }
}

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = SitesApi