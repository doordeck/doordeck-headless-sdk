package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.toTileLocksResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of tile-related API calls.
 */
actual object TilesApi {
    /**
     * @see TilesClient.getLocksBelongingToTileRequest
     */
    suspend fun getLocksBelongingToTile(tileId: UUID): TileLocksResponse {
        return TilesClient.getLocksBelongingToTileRequest(tileId.toString())
            .toTileLocksResponse()
    }

    /**
     * Async variant of [TilesApi.getLocksBelongingToTile] returning [CompletableFuture].
     */
    fun getLocksBelongingToTileAsync(tileId: UUID): CompletableFuture<TileLocksResponse> {
        return completableFuture { getLocksBelongingToTile(tileId) }
    }

    /**
     * @see TilesClient.associateMultipleLocksRequest
     */
    @SiteAdmin
    suspend fun associateMultipleLocks(
        tileId: UUID,
        siteId: UUID,
        lockIds: List<UUID>
    ) {
        return TilesClient.associateMultipleLocksRequest(
            tileId = tileId.toString(),
            siteId = siteId.toString(),
            lockIds = lockIds.map { it.toString() }
        )
    }

    /**
     * Async variant of [TilesApi.associateMultipleLocks] returning [CompletableFuture].
     */
    @SiteAdmin
    fun associateMultipleLocksAsync(
        tileId: UUID,
        siteId: UUID,
        lockIds: List<UUID>
    ): CompletableFuture<Unit> {
        return completableFuture {
            associateMultipleLocks(
                tileId = tileId,
                siteId = siteId,
                lockIds = lockIds
            )
        }
    }
}

/**
 * Defines the platform-specific implementation of [TilesApi]
 */
actual fun tiles(): TilesApi = TilesApi