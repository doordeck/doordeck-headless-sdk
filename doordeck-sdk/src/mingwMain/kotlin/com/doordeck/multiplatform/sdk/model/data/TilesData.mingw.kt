package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
data class GetLocksBelongingToTileData(
    val tileId: String
)

@Serializable
data class AssociateMultipleLocksData(
    val tileId: String,
    val siteId: String,
    val lockIds: List<String>
)