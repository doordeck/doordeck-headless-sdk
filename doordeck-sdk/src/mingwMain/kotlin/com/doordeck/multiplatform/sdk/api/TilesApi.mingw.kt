package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.data.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.model.data.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

actual object TilesApi {
    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    fun getLocksBelongingToTile(tileId: String): TileLocksResponse {
        return runBlocking { TilesClient.getLocksBelongingToTileRequest(tileId) }
    }

    @CName("getLocksBelongingToTileJson")
    fun getLocksBelongingToTileJson(data: String): String {
        return resultData {
            val getLocksBelongingToTileData = data.fromJson<GetLocksBelongingToTileData>()
            getLocksBelongingToTile(getLocksBelongingToTileData.tileId)
        }
    }

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>) {
        return runBlocking { TilesClient.associateMultipleLocksRequest(tileId, siteId, lockIds) }
    }

    @SiteAdmin
    @CName("associateMultipleLocksJson")
    fun associateMultipleLocksJson(data: String): String {
        return resultData {
            val associateMultipleLocksData = data.fromJson<AssociateMultipleLocksData>()
            associateMultipleLocks(
                tileId = associateMultipleLocksData.tileId,
                siteId = associateMultipleLocksData.siteId,
                lockIds = associateMultipleLocksData.lockIds
            )
        }
    }
}

actual fun tiles(): TilesApi = TilesApi