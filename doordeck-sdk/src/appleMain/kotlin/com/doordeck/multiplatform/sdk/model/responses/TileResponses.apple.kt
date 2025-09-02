package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.toNsUuid
import platform.Foundation.NSUUID

data class TileLocksResponse(
    val siteId: NSUUID,
    val tileId: NSUUID,
    val deviceIds: List<NSUUID>
)

internal fun BasicTileLocksResponse.toTileLocksResponse(): TileLocksResponse = TileLocksResponse(
    siteId = siteId.toNsUuid(),
    tileId = tileId.toNsUuid(),
    deviceIds = deviceIds.map { it.toNsUuid() }
)