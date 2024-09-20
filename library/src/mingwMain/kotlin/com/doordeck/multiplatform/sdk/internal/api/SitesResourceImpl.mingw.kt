package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

class SitesResourceImpl(
    httpClient: HttpClient
) : SitesClient(httpClient), SitesResource {

    override fun listSites(): String {
        return runBlocking { listSitesRequest() }.toJson()
    }

    override fun getLocksForSite(siteId: String): String {
        return runBlocking { getLocksForSiteRequest(siteId) }.toJson()
    }

    override fun getUsersForSite(siteId: String): String {
        return runBlocking { getUsersForSiteRequest(siteId) }.toJson()
    }
}