package com.doordeck.sdk.api

import com.doordeck.sdk.api.responses.EmptyResponse
import com.doordeck.sdk.api.responses.TileLocksResponse
import com.doordeck.sdk.internal.api.SiteAdmin
import kotlin.js.JsExport

@JsExport
interface TilesResource {

    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    fun getLocksBelongingToTile(tileId: String): TileLocksResponse

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    fun associateMultipleLocks(tileId: String, siteId: String, lockIds: Array<String>): EmptyResponse
}