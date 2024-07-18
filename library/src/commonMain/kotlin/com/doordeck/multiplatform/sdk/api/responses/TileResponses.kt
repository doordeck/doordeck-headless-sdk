package com.doordeck.multiplatform.sdk.api.responses

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class TileLocksResponse(
    val siteId: String,
    val tileId: String,
    val deviceIds: Array<String>
)