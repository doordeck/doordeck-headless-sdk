package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse

@JsExport
data class TileLocks(
    val siteId: String,
    val tileId: String,
    val deviceIds: List<String>
)

internal fun TileLocksResponse.toTileLocks(): TileLocks = TileLocks(
    siteId = siteId,
    tileId = tileId,
    deviceIds = deviceIds
)