package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.internal.api.SitesClient
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface SitesResource {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    fun listSites(): Promise<List<SiteResponse>>

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    fun getLocksForSite(siteId: String): Promise<List<SiteLocksResponse>>

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    fun getUsersForSite(siteId: String): Promise<List<UserForSiteResponse>>
}

private val sites = SitesResourceImpl(getKoin().get<SitesClient>())

@JsExport
actual fun sites(): SitesResource = sites