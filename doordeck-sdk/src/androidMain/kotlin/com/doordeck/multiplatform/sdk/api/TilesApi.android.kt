package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.toTileLocksResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of tile-related API calls.
 */
actual object TilesApi {
    /**
     * @see TilesClient.getLocksBelongingToTileRequest
     */
    suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return TilesClient.getLocksBelongingToTileRequest(tileId)
            .toTileLocksResponse()
    }

    /**
     * Async variant of [TilesApi.getLocksBelongingToTile] returning [CompletableFuture].
     */
    fun getLocksBelongingToTileAsync(tileId: String): CompletableFuture<TileLocksResponse> {
        return completableFuture { getLocksBelongingToTile(tileId) }
    }

    /**
     * @see TilesClient.associateMultipleLocksRequest
     */
    @SiteAdmin
    suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds)
    }

    /**
     * Async variant of [TilesApi.associateMultipleLocks] returning [CompletableFuture].
     */
    @SiteAdmin
    fun associateMultipleLocksAsync(tileId: String, siteId: String, lockIds: List<String>): CompletableFuture<Unit> {
        return completableFuture { associateMultipleLocks(tileId, siteId, lockIds) }
    }
}

/**
 * Defines the platform-specific implementation of [TilesApi]
 */
actual fun tiles(): TilesApi = TilesApi