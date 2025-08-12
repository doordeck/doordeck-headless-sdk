package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.network.ApiVersion
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.requests.AssociateMultipleLocksRequest
import com.doordeck.multiplatform.sdk.model.responses.BasicTileLocksResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlin.jvm.JvmSynthetic

/**
 * Internal implementation of the tiles API client.
 * Handles all  requests related to tiles.
 */
internal object TilesClient {
    /**
     * Retrieves all devices associated with the specified tile ID.
     *
     * @param tileId The tile unique identifier.
     * @return [BasicTileLocksResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/tiles/get-lock-belonging-to-tile-v3">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getLocksBelongingToTileRequest(tileId: String): BasicTileLocksResponse {
        return CloudHttpClient.client.get(Paths.getLocksBelongingToTilePath(tileId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_3)
        }.body()
    }

    /**
     * Associates multiple devices with a single tile.
     *
     * @param tileId The tile's unique identifier.
     * @param siteId The site's unique identifier.
     * @param lockIds The list of device unique identifiers.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/tiles/associate-multiple-locks-to-a-single-tile">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun associateMultipleLocksRequest(tileId: String, siteId: String, lockIds: List<String>) {
        CloudHttpClient.client.put(Paths.getAssociateMultipleLocksToASingleTilePath(tileId)) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(AssociateMultipleLocksRequest(siteId, lockIds))
        }
    }
}