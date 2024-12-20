package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal object TilesResourceImpl : TilesResource {

    override suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return TilesClient.getLocksBelongingToTileRequest(tileId)
    }

    override fun getLocksBelongingToTileAsync(tileId: String): CompletableFuture<TileLocksResponse> {
        return GlobalScope.future(Dispatchers.IO) { TilesClient.getLocksBelongingToTileRequest(tileId) }
    }

    override suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds)
    }

    override fun associateMultipleLocksAsync(tileId: String, siteId: String, lockIds: List<String>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }
}