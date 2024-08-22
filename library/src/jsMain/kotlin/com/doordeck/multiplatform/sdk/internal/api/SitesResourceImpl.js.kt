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
    private val httpClient: HttpClient
) : AbstractSitesClientImpl(httpClient), SitesResource {

    override fun listSites(): Promise<Array<SiteResponse>> {
        return GlobalScope.promise { listSitesRequest().toTypedArray() }
    }

    override fun getLocksForSite(siteId: String): Promise<Array<SiteLocksResponse>> {
        return GlobalScope.promise { getLocksForSiteRequest(siteId).toTypedArray() }
    }

    override fun getUsersForSite(siteId: String): Promise<Array<UserForSiteResponse>> {
        return GlobalScope.promise { getUsersForSiteRequest(siteId).toTypedArray() }
    }
}