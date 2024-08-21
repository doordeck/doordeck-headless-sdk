package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import io.ktor.client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

class SitesResourceImpl(
    private val httpClient: HttpClient
) : AbstractSitesClientImpl(httpClient), SitesResource {

    override suspend fun listSites(): Array<SiteResponse> {
        return listSitesRequest()
    }

    override suspend fun listSitesFuture(): CompletableFuture<Array<SiteResponse>> {
        return GlobalScope.future(Dispatchers.IO) { listSitesRequest() }
    }

    override suspend fun getLocksForSite(siteId: String): Array<SiteLocksResponse> {
        return getLocksForSiteRequest(siteId)
    }

    override fun getLocksForSiteFuture(siteId: String): CompletableFuture<Array<SiteLocksResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getLocksForSiteRequest(siteId) }
    }

    override suspend fun getUsersForSite(siteId: String): Array<UserForSiteResponse> {
        return getUsersForSiteRequest(siteId)
    }

    override fun getUsersForSiteFuture(siteId: String): CompletableFuture<Array<UserForSiteResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getUsersForSiteRequest(siteId) }
    }
}