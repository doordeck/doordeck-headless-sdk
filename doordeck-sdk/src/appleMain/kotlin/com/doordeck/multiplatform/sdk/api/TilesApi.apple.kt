package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.data.TileLocks
import com.doordeck.multiplatform.sdk.model.data.toTileLocks

/**
 * Platform-specific implementations of tile-related API calls.
 */
actual object TilesApi {
    /**
     * @see TilesClient.getLocksBelongingToTileRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksBelongingToTile(tileId: String): TileLocks {
        return TilesClient.getLocksBelongingToTileRequest(tileId)
            .toTileLocks()
    }

    /**
     * @see TilesClient.associateMultipleLocksRequest
     */
    @SiteAdmin
    @Throws(Exception::class)
    suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds)
    }
}

/**
 * Defines the platform-specific implementation of [TilesApi]
 */
actual fun tiles(): TilesApi = TilesApi