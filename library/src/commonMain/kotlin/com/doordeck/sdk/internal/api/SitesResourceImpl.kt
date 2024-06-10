package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.SitesResource
import com.doordeck.sdk.api.responses.SiteLocksResponse
import com.doordeck.sdk.api.responses.SiteResponse
import com.doordeck.sdk.api.responses.UserForSiteResponse
import com.doordeck.sdk.runBlocking
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class SitesResourceImpl(
    private val httpClient: HttpClient
) : SitesResource {

    override fun listSites(): Array<SiteResponse> = runBlocking {
        httpClient.get(Paths.getListSites()).body()
    }

    override fun getLocksForSite(siteId: String): Array<SiteLocksResponse> = runBlocking {
        httpClient.get(Paths.getLocksForSitePath(siteId)).body()
    }

    override fun getUsersForSite(siteId: String): Array<UserForSiteResponse> = runBlocking {
        httpClient.get(Paths.getUsersForSitePath(siteId)).body()
    }
}