package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.model.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.api.model.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

internal class TilesResourceImpl(
    httpClient: HttpClient
) : TilesClient(httpClient), TilesResource {

    override fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return runBlocking { getLocksBelongingToTileRequest(tileId) }
    }

    override fun getLocksBelongingToTileJson(data: String): String {
        val getLocksBelongingToTileData = data.fromJson<GetLocksBelongingToTileData>()
        return getLocksBelongingToTile(getLocksBelongingToTileData.tileId).toJson()
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return runBlocking { associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }

    override fun associateMultipleLocksJson(data: String) {
        val associateMultipleLocksData = data.fromJson<AssociateMultipleLocksData>()
        return associateMultipleLocks(associateMultipleLocksData.tileId, associateMultipleLocksData.siteId, associateMultipleLocksData.lockIds)
    }
}