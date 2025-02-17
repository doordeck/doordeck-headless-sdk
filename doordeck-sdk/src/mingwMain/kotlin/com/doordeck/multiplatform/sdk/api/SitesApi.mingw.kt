package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.data.GetLocksForSiteData
import com.doordeck.multiplatform.sdk.model.data.GetUsersForSiteData
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

actual object SitesApi {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    fun listSites(): List<SiteResponse> {
        return runBlocking { SitesClient.listSitesRequest() }
    }

    @CName("listSitesJson")
    fun listSitesJson(): String {
        return resultData {
            listSites()
        }
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    fun getLocksForSite(siteId: String): List<SiteLocksResponse> {
        return runBlocking { SitesClient.getLocksForSiteRequest(siteId) }
    }

    @CName("getLocksForSiteJson")
    fun getLocksForSiteJson(data: String): String {
        return resultData {
            val getLocksForSiteData = data.fromJson<GetLocksForSiteData>()
            getLocksForSite(getLocksForSiteData.siteId)
        }
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    fun getUsersForSite(siteId: String): List<UserForSiteResponse> {
        return runBlocking { SitesClient.getUsersForSiteRequest(siteId) }
    }

    @CName("getUsersForSiteJson")
    fun getUsersForSiteJson(data: String): String {
        return resultData {
            val getUsersForSiteData = data.fromJson<GetUsersForSiteData>()
            getUsersForSite(getUsersForSiteData.siteId)
        }
    }
}

actual fun sites(): SitesApi = SitesApi