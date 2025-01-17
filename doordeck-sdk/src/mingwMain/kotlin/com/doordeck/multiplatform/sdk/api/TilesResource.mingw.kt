package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.internal.api.SiteAdmin
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl

actual interface TilesResource {
    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    fun getLocksBelongingToTile(tileId: String): TileLocksResponse
    fun getLocksBelongingToTileJson(data: String): String

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>)
    @SiteAdmin
    fun associateMultipleLocksJson(data: String): String
}

actual fun tiles(): TilesResource = TilesResourceImpl