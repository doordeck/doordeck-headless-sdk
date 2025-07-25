package com.doordeck.multiplatform.sdk.model.responses

import kotlin.js.JsExport

@JsExport
data class TileLocksResponse(
    val siteId: String,
    val tileId: String,
    val deviceIds: List<String>
)

internal fun NetworkTileLocksResponse.toTileLocksResponse(): TileLocksResponse = TileLocksResponse(
    siteId = siteId,
    tileId = tileId,
    deviceIds = deviceIds
)