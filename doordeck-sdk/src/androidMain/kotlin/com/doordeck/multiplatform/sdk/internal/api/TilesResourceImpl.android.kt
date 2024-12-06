package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class TilesResourceImpl(
    private val tilesClient: TilesClient
) : TilesResource {

    override suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return tilesClient.getLocksBelongingToTileRequest(tileId)
    }

    override fun getLocksBelongingToTileAsync(tileId: String): CompletableFuture<TileLocksResponse> {
        return GlobalScope.future(Dispatchers.IO) { tilesClient.getLocksBelongingToTileRequest(tileId) }
    }

    override suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return tilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds)
    }

    override fun associateMultipleLocksAsync(tileId: String, siteId: String, lockIds: List<String>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { tilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }
}