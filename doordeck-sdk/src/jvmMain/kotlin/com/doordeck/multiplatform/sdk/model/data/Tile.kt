package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.toUUID
import java.util.UUID

data class TileLocks(
    val siteId: UUID,
    val tileId: UUID,
    val deviceIds: List<UUID>
)

internal fun TileLocksResponse.toTileLocks(): TileLocks = TileLocks(
    siteId = siteId.toUUID(),
    tileId = tileId.toUUID(),
    deviceIds = deviceIds.map { it.toUUID() }
)