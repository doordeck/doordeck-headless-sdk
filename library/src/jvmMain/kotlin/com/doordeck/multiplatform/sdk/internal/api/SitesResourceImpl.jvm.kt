package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

class SitesResourceImpl(
    httpClient: HttpClient
) : SitesClient(httpClient), SitesResource {

    override suspend fun listSites(): List<SiteResponse> {
        return listSitesRequest()
    }

    override fun listSitesAsync(): CompletableFuture<List<SiteResponse>> {
        return GlobalScope.future(Dispatchers.IO) { listSitesRequest() }
    }

    override suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return getLocksForSiteRequest(siteId)
    }

    override fun getLocksForSiteAsync(siteId: String): CompletableFuture<List<SiteLocksResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getLocksForSiteRequest(siteId) }
    }

    override suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return getUsersForSiteRequest(siteId)
    }

    override fun getUsersForSiteAsync(siteId: String): CompletableFuture<List<UserForSiteResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getUsersForSiteRequest(siteId) }
    }
}