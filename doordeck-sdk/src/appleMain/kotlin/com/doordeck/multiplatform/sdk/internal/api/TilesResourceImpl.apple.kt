package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse

internal class TilesResourceImpl(
    private val tilesClient: TilesClient
) : TilesResource {

    override suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return tilesClient.getLocksBelongingToTileRequest(tileId)
    }

    override suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return tilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds)
    }
}