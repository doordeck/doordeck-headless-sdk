package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.responses.BasicTileLocksResponse

data class TileLocks(
    val siteId: String,
    val tileId: String,
    val deviceIds: List<String>
)

internal fun BasicTileLocksResponse.toTileLocks(): TileLocks = TileLocks(
    siteId = siteId,
    tileId = tileId,
    deviceIds = deviceIds
)