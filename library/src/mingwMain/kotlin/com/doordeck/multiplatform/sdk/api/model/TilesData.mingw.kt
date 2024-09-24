package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
class GetLocksBelongingToTileData(
    val tileId: String
)

@Serializable
class AssociateMultipleLocksData(
    val tileId: String,
    val siteId: String,
    val lockIds: List<String>
)