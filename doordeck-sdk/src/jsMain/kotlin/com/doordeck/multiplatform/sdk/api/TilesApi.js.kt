package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

/**
 * Platform-specific implementations of tile-related API calls.
 */
@JsExport
actual object TilesApi {
    /**
     * @see TilesClient.getLocksBelongingToTileRequest
     */
    fun getLocksBelongingToTile(tileId: String): Promise<TileLocksResponse> {
        return promise { TilesClient.getLocksBelongingToTileRequest(tileId) }
    }

    /**
     * @see TilesClient.associateMultipleLocksRequest
     */
    @SiteAdmin
    fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>): Promise<dynamic> {
        return promise { TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }
}

private val tiles = TilesApi

/**
 * Defines the platform-specific implementation of [TilesApi]
 */
@JsExport
actual fun tiles(): TilesApi = tiles