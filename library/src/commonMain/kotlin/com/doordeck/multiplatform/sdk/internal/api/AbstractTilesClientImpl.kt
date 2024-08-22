package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.requests.AssociateMultipleLocksRequest
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

abstract class AbstractTilesClientImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl() {

    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    suspend fun getLocksBelongingToTileRequest(tileId: String): TileLocksResponse {
        return httpClient.get(Paths.getLocksBelongingToTilePath(tileId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_3)
        }
    }

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    suspend fun associateMultipleLocksRequest(tileId: String, siteId: String, lockIds: List<String>) {
        httpClient.put<Unit>(Paths.getAssociateMultipleLocksToASingleTilePath(tileId)) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(AssociateMultipleLocksRequest(siteId, lockIds))
        }
    }
}