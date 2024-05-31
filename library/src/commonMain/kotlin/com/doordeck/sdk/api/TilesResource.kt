package com.doordeck.sdk.api

import com.doordeck.sdk.api.responses.TileLocksResponse
import kotlin.js.JsExport

@JsExport
interface TilesResource {

    fun getLocksBelongingToTile(tileId: String): TileLocksResponse
    fun associateTileWithLock(tileId: String, lockId: String)
    fun disassociateTileFromLock(tileId: String, lockId: String)
    fun associateMultipleLocks(tileId: String, siteId: String, lockIds: Array<String>)
}