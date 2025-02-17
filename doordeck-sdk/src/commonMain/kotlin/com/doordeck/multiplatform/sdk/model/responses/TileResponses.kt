package com.doordeck.multiplatform.sdk.model.responses

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class TileLocksResponse(
    val siteId: String,
    val tileId: String,
    val deviceIds: List<String>
)