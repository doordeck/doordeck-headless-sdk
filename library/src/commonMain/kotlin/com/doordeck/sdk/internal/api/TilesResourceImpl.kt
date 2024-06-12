package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.TilesResource
import com.doordeck.sdk.api.requests.AssociateMultipleLocksRequest
import com.doordeck.sdk.api.responses.EmptyResponse
import com.doordeck.sdk.api.responses.TileLocksResponse
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class TilesResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), TilesResource {

    override fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return httpClient.get(Paths.getLocksBelongingToTilePath(tileId))
    }

    override fun associateTileWithLock(tileId: String, lockId: String): EmptyResponse {
        return httpClient.putEmpty(Paths.getAssociateTileWithLockPath(tileId, lockId))
    }

    override fun disassociateTileFromLock(tileId: String, lockId: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getDisassociateTileFromLockPath(tileId, lockId))
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: Array<String>): EmptyResponse {
        return httpClient.putEmpty(Paths.getAssociateMultipleLocksToASingleTilePath(tileId)) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(AssociateMultipleLocksRequest(siteId, lockIds))
        }
    }
}