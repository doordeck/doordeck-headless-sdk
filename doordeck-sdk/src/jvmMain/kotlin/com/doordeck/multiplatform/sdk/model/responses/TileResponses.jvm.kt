package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.toUuid
import java.util.UUID

data class TileLocksResponse(
    val siteId: UUID,
    val tileId: UUID,
    val deviceIds: List<UUID>
)

internal fun BasicTileLocksResponse.toTileLocksResponse(): TileLocksResponse = TileLocksResponse(
    siteId = siteId.toUuid(),
    tileId = tileId.toUuid(),
    deviceIds = deviceIds.map { it.toUuid() }
)