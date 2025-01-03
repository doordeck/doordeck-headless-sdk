package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

internal object SitesResourceImpl : SitesResource {

    override fun listSites(): Promise<List<SiteResponse>> {
        return promise { SitesClient.listSitesRequest() }
    }

    override fun getLocksForSite(siteId: String): Promise<List<SiteLocksResponse>> {
        return promise { SitesClient.getLocksForSiteRequest(siteId) }
    }

    override fun getUsersForSite(siteId: String): Promise<List<UserForSiteResponse>> {
        return promise { SitesClient.getUsersForSiteRequest(siteId) }
    }
}