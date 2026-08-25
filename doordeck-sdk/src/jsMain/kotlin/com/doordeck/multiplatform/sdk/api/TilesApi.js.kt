package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.toTileLocksResponse
import kotlin.js.collections.JsArray
import kotlin.js.collections.toList

/**
 * Platform-specific implementations of tile-related API calls.
 */
@JsExport
actual object TilesApi {
    /**
     * @see TilesClient.getLocksBelongingToTileRequest
     */
    suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse = TilesClient
        .getLocksBelongingToTileRequest(tileId)
        .toTileLocksResponse()

    /**
     * @see TilesClient.associateMultipleLocksRequest
     */
    @SiteAdmin
    suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: JsArray<String>): dynamic = TilesClient
        .associateMultipleLocksRequest(
            tileId = tileId,
            siteId = siteId,
            lockIds = lockIds.toList()
        )
}

private val tiles = TilesApi

/**
 * Defines the platform-specific implementation of [TilesApi]
 */
@JsExport
actual fun tiles(): TilesApi = tiles