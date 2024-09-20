package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

class TilesResourceImpl(
    httpClient: HttpClient
) : TilesClient(httpClient), TilesResource {

    override fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return runBlocking { getLocksBelongingToTileRequest(tileId) }
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return runBlocking { associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }
}