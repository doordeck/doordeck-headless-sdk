package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse

internal class SitesResourceImpl(
    private val sitesClient: SitesClient
) : SitesResource {

    override suspend fun listSites(): List<SiteResponse> {
        return sitesClient.listSitesRequest()
    }

    override suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return sitesClient.getLocksForSiteRequest(siteId)
    }

    override suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return sitesClient.getUsersForSiteRequest(siteId)
    }
}