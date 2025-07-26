package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse

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