package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

internal object TilesResourceImpl : TilesResource {

    override suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return TilesClient.getLocksBelongingToTileRequest(tileId)
    }

    override fun getLocksBelongingToTileAsync(tileId: String): CompletableFuture<TileLocksResponse> {
        return completableFuture { getLocksBelongingToTile(tileId) }
    }

    override suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds)
    }

    override fun associateMultipleLocksAsync(tileId: String, siteId: String, lockIds: List<String>): CompletableFuture<Unit> {
        return completableFuture { associateMultipleLocks(tileId, siteId, lockIds) }
    }
}