package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

class TilesResourceImpl(
    private val httpClient: HttpClient
) : AbstractTilesClientImpl(httpClient), TilesResource {

    override suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return getLocksBelongingToTileRequest(tileId)
    }

    override fun getLocksBelongingToTileFuture(tileId: String): CompletableFuture<TileLocksResponse> {
        return GlobalScope.future(Dispatchers.IO) { getLocksBelongingToTileRequest(tileId) }
    }

    override suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return associateMultipleLocksRequest(tileId, siteId, lockIds)
    }

    override fun associateMultipleLocksFuture(tileId: String, siteId: String, lockIds: List<String>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }
}