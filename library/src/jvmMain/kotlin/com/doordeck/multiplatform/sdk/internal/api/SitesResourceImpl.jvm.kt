package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import io.ktor.client.*

class SitesResourceImpl(
    private val httpClient: HttpClient
) : AbstractSitesClientImpl(httpClient), SitesResource {

    override suspend fun listSites(): Array<SiteResponse> {
        return listSitesRequest()
    }

    override suspend fun getLocksForSite(siteId: String): Array<SiteLocksResponse> {
        return getLocksForSiteRequest(siteId)
    }

    override suspend fun getUsersForSite(siteId: String): Array<UserForSiteResponse> {
        return getUsersForSiteRequest(siteId)
    }
}