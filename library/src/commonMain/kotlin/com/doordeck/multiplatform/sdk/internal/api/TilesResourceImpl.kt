package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.requests.AssociateMultipleLocksRequest
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class TilesResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), TilesResource {

    override fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return httpClient.get(Paths.getLocksBelongingToTilePath(tileId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_3)
        }
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: Array<String>) {
        httpClient.putEmpty(Paths.getAssociateMultipleLocksToASingleTilePath(tileId)) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(AssociateMultipleLocksRequest(siteId, lockIds))
        }
    }
}