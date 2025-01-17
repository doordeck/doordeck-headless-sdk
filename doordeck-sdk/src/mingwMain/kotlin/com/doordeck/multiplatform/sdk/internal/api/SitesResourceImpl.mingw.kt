package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.model.GetLocksForSiteData
import com.doordeck.multiplatform.sdk.api.model.GetUsersForSiteData
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

internal object SitesResourceImpl : SitesResource {

    override fun listSites(): List<SiteResponse> {
        return runBlocking { SitesClient.listSitesRequest() }
    }

    override fun listSitesJson(): String {
        return resultData {
            listSites()
        }
    }

    override fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return runBlocking { SitesClient.getLocksForSiteRequest(siteId) }
    }

    override fun getLocksForSiteJson(data: String): String {
        return resultData {
            val getLocksForSiteData = data.fromJson<GetLocksForSiteData>()
            getLocksForSite(getLocksForSiteData.siteId)
        }
    }

    override fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return runBlocking { SitesClient.getUsersForSiteRequest(siteId) }
    }

    override fun getUsersForSiteJson(data: String): String {
        return resultData {
            val getUsersForSiteData = data.fromJson<GetUsersForSiteData>()
            getUsersForSite(getUsersForSiteData.siteId)
        }
    }
}