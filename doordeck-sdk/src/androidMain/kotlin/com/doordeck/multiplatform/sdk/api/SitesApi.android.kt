package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserForSiteResponse
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
    suspend fun listSites(): List<SiteResponse> = SitesClient
        .listSitesRequest()
        .toSiteResponse()

    /**
     * Async variant of [SitesApi.listSites] returning [CompletableFuture].
     */
    fun listSitesAsync(): CompletableFuture<List<SiteResponse>> = completableFuture {
        listSites()
    }

    /**
     * @see SitesClient.getLocksForSiteRequest
     */
    suspend fun getLocksForSite(siteId: UUID): List<SiteLocksResponse> = SitesClient
        .getLocksForSiteRequest(siteId.toString())
        .toLockResponse()

    /**
     * Async variant of [SitesApi.getLocksForSite] returning [CompletableFuture].
     */
    fun getLocksForSiteAsync(siteId: UUID): CompletableFuture<List<SiteLocksResponse>> = completableFuture {
        getLocksForSite(siteId)
    }

    /**
     * @see SitesClient.getUsersForSiteRequest
     */
    suspend fun getUsersForSite(siteId: UUID): List<UserForSiteResponse> = SitesClient
        .getUsersForSiteRequest(siteId.toString())
        .toUserForSiteResponse()

    /**
     * Async variant of [SitesApi.getUsersForSite] returning [CompletableFuture].
     */
    fun getUsersForSiteAsync(siteId: UUID): CompletableFuture<List<UserForSiteResponse>> = completableFuture {
        getUsersForSite(siteId)
    }
}

/**
 * Defines the platform-specific implementation of [SitesApi]
 */
actual fun sites(): SitesApi = SitesApi