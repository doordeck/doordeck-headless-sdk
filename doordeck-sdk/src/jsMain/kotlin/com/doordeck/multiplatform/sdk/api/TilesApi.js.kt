package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.toTileLocksResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise
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
    fun getLocksBelongingToTile(tileId: String): Promise<TileLocksResponse> = promise {
        TilesClient.getLocksBelongingToTileRequest(tileId)
            .toTileLocksResponse()
    }

    /**
     * @see TilesClient.associateMultipleLocksRequest
     */
    @SiteAdmin
    fun associateMultipleLocks(tileId: String, siteId: String, lockIds: JsArray<String>): Promise<dynamic> = promise {
        TilesClient.associateMultipleLocksRequest(
            tileId = tileId,
            siteId = siteId,
            lockIds = lockIds.toList()
        )
    }
}

private val tiles = TilesApi

/**
 * Defines the platform-specific implementation of [TilesApi]
 */
@JsExport
actual fun tiles(): TilesApi = tiles