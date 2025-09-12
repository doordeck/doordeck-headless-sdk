package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CStringCallback
import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.data.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.model.data.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.util.handleCallback
import com.doordeck.multiplatform.sdk.util.fromJson

actual object TilesApi {
    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    @CName("getLocksBelongingToTile")
    fun getLocksBelongingToTile(data: String, callback: CStringCallback) = callback.handleCallback {
        val getLocksBelongingToTileData = data.fromJson<GetLocksBelongingToTileData>()
        TilesClient.getLocksBelongingToTileRequest(getLocksBelongingToTileData.tileId)
    }

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    @CName("associateMultipleLocks")
    fun associateMultipleLocks(data: String, callback: CStringCallback) = callback.handleCallback {
        val associateMultipleLocksData = data.fromJson<AssociateMultipleLocksData>()
        TilesClient.associateMultipleLocksRequest(
            tileId = associateMultipleLocksData.tileId,
            siteId = associateMultipleLocksData.siteId,
            lockIds = associateMultipleLocksData.lockIds
        )
    }
}

actual fun tiles(): TilesApi = TilesApi