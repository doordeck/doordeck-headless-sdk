package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.model.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.api.model.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.runBlocking

internal class TilesResourceImpl(
    private val tilesClient: TilesClient
) : TilesResource {

    override fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return runBlocking { tilesClient.getLocksBelongingToTileRequest(tileId) }
    }

    override fun getLocksBelongingToTileJson(data: String): String {
        val getLocksBelongingToTileData = data.fromJson<GetLocksBelongingToTileData>()
        return getLocksBelongingToTile(getLocksBelongingToTileData.tileId).toJson()
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return runBlocking { tilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }

    override fun associateMultipleLocksJson(data: String) {
        val associateMultipleLocksData = data.fromJson<AssociateMultipleLocksData>()
        return associateMultipleLocks(associateMultipleLocksData.tileId, associateMultipleLocksData.siteId, associateMultipleLocksData.lockIds)
    }
}