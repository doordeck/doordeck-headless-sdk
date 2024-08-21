package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import io.ktor.client.*

class TilesResourceImpl(
    private val httpClient: HttpClient
) : AbstractTilesClientImpl(httpClient), TilesResource {

    override suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return getLocksBelongingToTileRequest(tileId)
    }

    override suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return associateMultipleLocksRequest(tileId, siteId, lockIds)
    }
}