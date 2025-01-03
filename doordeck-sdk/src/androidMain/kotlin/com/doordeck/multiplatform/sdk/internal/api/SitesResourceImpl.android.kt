package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

internal object SitesResourceImpl : SitesResource {

    override suspend fun listSites(): List<SiteResponse> {
        return SitesClient.listSitesRequest()
    }

    override fun listSitesAsync(): CompletableFuture<List<SiteResponse>> {
        return completableFuture { listSites() }
    }

    override suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return SitesClient.getLocksForSiteRequest(siteId)
    }

    override fun getLocksForSiteAsync(siteId: String): CompletableFuture<List<SiteLocksResponse>> {
        return completableFuture { getLocksForSite(siteId) }
    }

    override suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return SitesClient.getUsersForSiteRequest(siteId)
    }

    override fun getUsersForSiteAsync(siteId: String): CompletableFuture<List<UserForSiteResponse>> {
        return completableFuture { getUsersForSite(siteId) }
    }
}