package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

class TilesResourceImpl(
    httpClient: HttpClient
) : TilesClient(httpClient), TilesResource {

    override fun getLocksBelongingToTile(tileId: String): Promise<TileLocksResponse> {
        return GlobalScope.promise { getLocksBelongingToTileRequest(tileId) }
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: Array<String>): Promise<Unit> {
        return GlobalScope.promise { associateMultipleLocksRequest(tileId, siteId, lockIds.toList()) }
    }
}