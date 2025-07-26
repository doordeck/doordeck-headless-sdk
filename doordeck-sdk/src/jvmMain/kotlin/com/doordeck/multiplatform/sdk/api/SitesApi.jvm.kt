package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.data.SiteLocks
import com.doordeck.multiplatform.sdk.model.data.Site
import com.doordeck.multiplatform.sdk.model.data.UserForSite
import com.doordeck.multiplatform.sdk.model.data.toLock
import com.doordeck.multiplatform.sdk.model.data.toSite
import com.doordeck.multiplatform.sdk.model.data.toUserForSite
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of sites-related API calls.
 */
actual object SitesApi {
    /**
     * @see SitesClient.listSitesRequest
     */
    suspend fun listSites(): List<Site> {
        return SitesClient.listSitesRequest()
            .toSite()
    }

    /**
     * Async variant of [SitesApi.listSites] returning [CompletableFuture].
     */
    fun listSitesAsync(): CompletableFuture<List<Site>> {
        return completableFuture { listSites() }
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    suspend fun getLocksForSite(siteId: UUID): List<SiteLocks> {
        return SitesClient.getLocksForSiteRequest(siteId.toString())
            .toLock()
    }

    /**
     * Async variant of [SitesApi.getLocksForSite] returning [CompletableFuture].
     */
    fun getLocksForSiteAsync(siteId: UUID): CompletableFuture<List<SiteLocks>> {
        return completableFuture { getLocksForSite(siteId) }
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    suspend fun getUsersForSite(siteId: UUID): List<UserForSite> {
        return SitesClient.getUsersForSiteRequest(siteId.toString())
            .toUserForSite()
    }

    /**
     * Async variant of [SitesApi.getUsersForSite] returning [CompletableFuture].
     */
    fun getUsersForSiteAsync(siteId: UUID): CompletableFuture<List<UserForSite>> {
        return completableFuture { getUsersForSite(siteId) }
    }
}

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = SitesApi