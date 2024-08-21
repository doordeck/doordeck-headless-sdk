package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import io.ktor.client.*
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface SitesResource {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    suspend fun listSites(): Array<SiteResponse>

    suspend fun listSitesFuture(): CompletableFuture<Array<SiteResponse>>

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    suspend fun getLocksForSite(siteId: String): Array<SiteLocksResponse>

    fun getLocksForSiteFuture(siteId: String): CompletableFuture<Array<SiteLocksResponse>>

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    suspend fun getUsersForSite(siteId: String): Array<UserForSiteResponse>

    fun getUsersForSiteFuture(siteId: String): CompletableFuture<Array<UserForSiteResponse>>
}

actual fun sites(): SitesResource = SitesResourceImpl(getKoin().get<HttpClient>(named("cloudHttpClient")))