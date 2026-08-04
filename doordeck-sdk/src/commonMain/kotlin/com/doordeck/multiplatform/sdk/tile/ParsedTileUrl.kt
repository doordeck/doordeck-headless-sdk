package com.doordeck.multiplatform.sdk.tile

import kotlin.js.JsExport

@JsExport
data class ParsedTileUrl(
    val tileId: String,
    val url: String
)