package com.doordeck.sdk.api

import com.doordeck.sdk.api.responses.SiteLocksResponse
import com.doordeck.sdk.api.responses.SiteResponse
import com.doordeck.sdk.api.responses.UserForSiteResponse
import kotlin.js.JsExport

@JsExport
interface SitesResource {

    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    fun listSites(): Array<SiteResponse>

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    fun getLocksForSite(siteId: String): Array<SiteLocksResponse>

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    fun getUsersForSite(siteId: String): Array<UserForSiteResponse>
}