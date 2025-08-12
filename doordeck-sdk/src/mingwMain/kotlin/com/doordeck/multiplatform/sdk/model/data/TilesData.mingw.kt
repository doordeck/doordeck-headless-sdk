package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
internal data class GetLocksBelongingToTileData(
    val tileId: String
)

@Serializable
internal data class AssociateMultipleLocksData(
    val tileId: String,
    val siteId: String,
    val lockIds: List<String>
)