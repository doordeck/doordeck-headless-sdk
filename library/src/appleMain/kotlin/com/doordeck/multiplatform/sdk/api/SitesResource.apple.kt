package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin

actual interface SitesResource {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun listSites(): List<SiteResponse>

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun getLocksForSite(siteId: String): List<SiteLocksResponse>

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun getUsersForSite(siteId: String): List<UserForSiteResponse>
}

actual fun sites(): SitesResource = SitesResourceImpl(getKoin().get<HttpClient>(named("cloudHttpClient")))