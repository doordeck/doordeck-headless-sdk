package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import io.ktor.client.*

class SitesResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), SitesResource {

    override fun listSites(): Array<SiteResponse> {
        return httpClient.get(Paths.getListSites())
    }

    override fun getLocksForSite(siteId: String): Array<SiteLocksResponse> {
        return httpClient.get(Paths.getLocksForSitePath(siteId))
    }

    override fun getUsersForSite(siteId: String): Array<UserForSiteResponse> {
        return httpClient.get(Paths.getUsersForSitePath(siteId))
    }
}