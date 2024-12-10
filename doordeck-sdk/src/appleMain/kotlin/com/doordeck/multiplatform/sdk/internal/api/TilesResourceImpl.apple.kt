package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse

internal object TilesResourceImpl : TilesResource {

    override suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return TilesClient.getLocksBelongingToTileRequest(tileId)
    }

    override suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds)
    }
}