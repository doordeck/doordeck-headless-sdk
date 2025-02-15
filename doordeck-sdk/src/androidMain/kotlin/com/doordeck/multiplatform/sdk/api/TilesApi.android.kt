package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

actual object TilesApi {
    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return TilesClient.getLocksBelongingToTileRequest(tileId)
    }

    fun getLocksBelongingToTileAsync(tileId: String): CompletableFuture<TileLocksResponse> {
        return completableFuture { getLocksBelongingToTile(tileId) }
    }

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds)
    }

    @SiteAdmin
    fun associateMultipleLocksAsync(tileId: String, siteId: String, lockIds: List<String>): CompletableFuture<Unit> {
        return completableFuture { associateMultipleLocks(tileId, siteId, lockIds) }
    }
}

actual fun tiles(): TilesApi = TilesApi