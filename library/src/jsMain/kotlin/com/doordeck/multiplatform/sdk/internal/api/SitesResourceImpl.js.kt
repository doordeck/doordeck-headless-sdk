package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

class SitesResourceImpl(
    httpClient: HttpClient
) : SitesClient(httpClient), SitesResource {

    override fun listSites(): Promise<List<SiteResponse>> {
        return GlobalScope.promise { listSitesRequest() }
    }

    override fun getLocksForSite(siteId: String): Promise<List<SiteLocksResponse>> {
        return GlobalScope.promise { getLocksForSiteRequest(siteId) }
    }

    override fun getUsersForSite(siteId: String): Promise<List<UserForSiteResponse>> {
        return GlobalScope.promise { getUsersForSiteRequest(siteId) }
    }
}