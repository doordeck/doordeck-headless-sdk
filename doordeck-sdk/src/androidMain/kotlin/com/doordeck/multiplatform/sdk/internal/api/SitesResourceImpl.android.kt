package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal object SitesResourceImpl : SitesResource {

    override suspend fun listSites(): List<SiteResponse> {
        return SitesClient.listSitesRequest()
    }

    override fun listSitesAsync(): CompletableFuture<List<SiteResponse>> {
        return GlobalScope.future(Dispatchers.IO) { SitesClient.listSitesRequest() }
    }

    override suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return SitesClient.getLocksForSiteRequest(siteId)
    }

    override fun getLocksForSiteAsync(siteId: String): CompletableFuture<List<SiteLocksResponse>> {
        return GlobalScope.future(Dispatchers.IO) { SitesClient.getLocksForSiteRequest(siteId) }
    }

    override suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return SitesClient.getUsersForSiteRequest(siteId)
    }

    override fun getUsersForSiteAsync(siteId: String): CompletableFuture<List<UserForSiteResponse>> {
        return GlobalScope.future(Dispatchers.IO) { SitesClient.getUsersForSiteRequest(siteId) }
    }
}