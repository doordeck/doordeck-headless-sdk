package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import io.ktor.client.*
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin

actual interface SitesResource {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    suspend fun listSites(): List<SiteResponse> // Probably not suspend
}

actual fun sites(): SitesResource = SitesResourceImpl(getKoin().get<HttpClient>(named("cloudHttpClient")))