package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.SitesResource
import com.doordeck.sdk.api.responses.SiteLocksResponse
import com.doordeck.sdk.api.responses.SiteResponse
import com.doordeck.sdk.api.responses.UserForSiteResponse
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