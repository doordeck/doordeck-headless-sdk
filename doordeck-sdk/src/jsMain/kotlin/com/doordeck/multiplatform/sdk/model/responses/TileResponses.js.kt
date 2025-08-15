package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.toJsArray
import kotlin.js.collections.JsArray

@JsExport
data class TileLocksResponse(
    val siteId: String,
    val tileId: String,
    val deviceIds: JsArray<String>
)

internal fun BasicTileLocksResponse.toTileLocksResponse(): TileLocksResponse = TileLocksResponse(
    siteId = siteId,
    tileId = tileId,
    deviceIds = deviceIds.toJsArray()
)