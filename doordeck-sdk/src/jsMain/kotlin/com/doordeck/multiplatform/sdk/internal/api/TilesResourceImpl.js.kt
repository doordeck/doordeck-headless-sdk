package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal class TilesResourceImpl(
    private val tilesClient: TilesClient
) : TilesResource {

    override fun getLocksBelongingToTile(tileId: String): Promise<TileLocksResponse> {
        return GlobalScope.promise { tilesClient.getLocksBelongingToTileRequest(tileId) }
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>): Promise<Unit> {
        return GlobalScope.promise { tilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }
}