package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.toTileLocksResponse
import platform.Foundation.NSUUID

/**
 * Platform-specific implementations of tile-related API calls.
 */
actual object TilesApi {
    /**
     * @see TilesClient.getLocksBelongingToTileRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksBelongingToTile(tileId: NSUUID): TileLocksResponse = TilesClient
        .getLocksBelongingToTileRequest(tileId.UUIDString)
        .toTileLocksResponse()

    /**
     * @see TilesClient.associateMultipleLocksRequest
     */
    @SiteAdmin
    @Throws(Exception::class)
    suspend fun associateMultipleLocks(tileId: NSUUID, siteId: NSUUID, lockIds: List<NSUUID>) = TilesClient
        .associateMultipleLocksRequest(
            tileId = tileId.UUIDString,
            siteId = siteId.UUIDString,
            lockIds = lockIds.map { it.UUIDString }
        )
}

/**
 * Defines the platform-specific implementation of [TilesApi]
 */
actual fun tiles(): TilesApi = TilesApi