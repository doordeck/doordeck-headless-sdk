package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

@JsExport
actual object TilesApi {
    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    fun getLocksBelongingToTile(tileId: String): Promise<TileLocksResponse> {
        return promise { TilesClient.getLocksBelongingToTileRequest(tileId) }
    }

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>): Promise<dynamic> {
        return promise { TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }
}

private val tiles = TilesApi

@JsExport
actual fun tiles(): TilesApi = tiles