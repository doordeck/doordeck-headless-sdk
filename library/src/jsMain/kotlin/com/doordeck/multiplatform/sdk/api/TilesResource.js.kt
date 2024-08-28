package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.internal.api.SiteAdmin
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface TilesResource {
    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    fun getLocksBelongingToTile(tileId: String): Promise<TileLocksResponse>

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    fun associateMultipleLocks(tileId: String, siteId: String, lockIds: Array<String>): Promise<dynamic>
}

private val tiles = TilesResourceImpl(getKoin().get<HttpClient>(named("cloudHttpClient")))

@JsExport
actual fun tiles(): TilesResource = tiles