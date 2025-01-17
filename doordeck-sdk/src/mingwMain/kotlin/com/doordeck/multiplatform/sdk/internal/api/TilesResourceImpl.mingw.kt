package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.model.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.api.model.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

internal object TilesResourceImpl : TilesResource {

    override fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return runBlocking { TilesClient.getLocksBelongingToTileRequest(tileId) }
    }

    override fun getLocksBelongingToTileJson(data: String): String {
        return resultData {
            val getLocksBelongingToTileData = data.fromJson<GetLocksBelongingToTileData>()
            getLocksBelongingToTile(getLocksBelongingToTileData.tileId)
        }
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return runBlocking { TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }

    override fun associateMultipleLocksJson(data: String): String {
        return resultData {
            val associateMultipleLocksData = data.fromJson<AssociateMultipleLocksData>()
            associateMultipleLocks(associateMultipleLocksData.tileId, associateMultipleLocksData.siteId, associateMultipleLocksData.lockIds)
        }
    }
}