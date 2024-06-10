package com.doordeck.sdk.api

import com.doordeck.sdk.api.responses.SiteLocksResponse
import com.doordeck.sdk.api.responses.SiteResponse
import com.doordeck.sdk.api.responses.UserForSiteResponse
import kotlin.js.JsExport

@JsExport
interface SitesResource {

    fun listSites(): Array<SiteResponse>
    fun getLocksForSite(siteId: String): Array<SiteLocksResponse>
    fun getUsersForSite(siteId: String): Array<UserForSiteResponse>
}