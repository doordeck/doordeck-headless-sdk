package com.doordeck.multiplatform.sdk.model.responses

@JsExport
data class TileLocksResponse(
    val siteId: String,
    val tileId: String,
    val deviceIds: List<String>
)

internal fun BasicTileLocksResponse.toTileLocksResponse(): TileLocksResponse = TileLocksResponse(
    siteId = siteId,
    tileId = tileId,
    deviceIds = deviceIds
)