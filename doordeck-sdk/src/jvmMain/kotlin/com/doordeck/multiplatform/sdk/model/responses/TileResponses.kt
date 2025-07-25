package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.toUUID
import java.util.UUID

data class TileLocksResponse(
    val siteId: UUID,
    val tileId: UUID,
    val deviceIds: List<UUID>
)

internal fun NetworkTileLocksResponse.toTileLocksResponse(): TileLocksResponse = TileLocksResponse(
    siteId = siteId.toUUID(),
    tileId = tileId.toUUID(),
    deviceIds = deviceIds.map { it.toUUID() }
)