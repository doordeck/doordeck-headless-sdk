package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import io.ktor.client.*

class SitesResourceImpl(
    private val httpClient: HttpClient
) : AbstractSitesClientImpl(httpClient), SitesResource {

    override suspend fun listSites(): List<SiteResponse> {
        return listSitesRequest().toList() // Probably not suspend
    }
}