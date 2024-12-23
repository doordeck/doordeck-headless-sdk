package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.model.GetLocksForSiteData
import com.doordeck.multiplatform.sdk.api.model.GetUsersForSiteData
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.runBlocking

internal object SitesResourceImpl : SitesResource {

    override fun listSites(): List<SiteResponse> {
        return runBlocking { SitesClient.listSitesRequest() }
    }

    override fun listSitesJson(): String {
        return listSites().toJson()
    }

    override fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return runBlocking { SitesClient.getLocksForSiteRequest(siteId) }
    }

    override fun getLocksForSiteJson(data: String): String {
        val getLocksForSiteData = data.fromJson<GetLocksForSiteData>()
        return getLocksForSite(getLocksForSiteData.siteId).toJson()
    }

    override fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return runBlocking { SitesClient.getUsersForSiteRequest(siteId) }
    }

    override fun getUsersForSiteJson(data: String): String {
        val getUsersForSiteData = data.fromJson<GetUsersForSiteData>()
        return getUsersForSite(getUsersForSiteData.siteId).toJson()
    }
}