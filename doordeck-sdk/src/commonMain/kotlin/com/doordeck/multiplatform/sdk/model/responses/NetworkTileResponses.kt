package com.doordeck.multiplatform.sdk.model.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class NetworkTileLocksResponse(
    val siteId: String,
    val tileId: String,
    val deviceIds: List<String>
)