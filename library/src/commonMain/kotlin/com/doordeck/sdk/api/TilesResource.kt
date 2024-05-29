package com.doordeck.sdk.api

import kotlin.js.JsExport

@JsExport
interface TilesResource {

    fun getLocksBelongingToTile(tileId: String) // TODO
    fun associateTileWithLock(tileId: String, deviceId: String) // TODO
    fun disassociateTileFromLock(tileId: String, deviceId: String) // TODO
    fun associateMultipleLocks(tileId: String) // TODO
}