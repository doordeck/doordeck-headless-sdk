package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import io.ktor.client.HttpClient

class SitesResourceImpl(
    httpClient: HttpClient
) : SitesClient(httpClient), SitesResource {

    override suspend fun listSites(): List<SiteResponse> {
        return listSitesRequest()
    }

    override fun listSites2(): List<SiteResponse> {
        return listOf(SiteResponse("5", "test", "red", 0.0, 0.0, 5, "back", "today", "now"))
    }

    override suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return getLocksForSiteRequest(siteId)
    }

    override suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return getUsersForSiteRequest(siteId)
    }
}