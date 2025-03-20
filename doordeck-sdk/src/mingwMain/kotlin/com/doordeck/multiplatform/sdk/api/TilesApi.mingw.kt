package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.SiteAdmin
import com.doordeck.multiplatform.sdk.clients.TilesClient
import com.doordeck.multiplatform.sdk.model.data.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.model.data.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.launchCallback
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer

actual object TilesApi {
    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    @CName("getLocksBelongingToTile")
    fun getLocksBelongingToTile(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getLocksBelongingToTileData = data.fromJson<GetLocksBelongingToTileData>()
                TilesClient.getLocksBelongingToTileRequest(getLocksBelongingToTileData.tileId)
            },
            callback = callback
        )
    }

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    @CName("associateMultipleLocks")
    fun associateMultipleLocks(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val associateMultipleLocksData = data.fromJson<AssociateMultipleLocksData>()
                TilesClient.associateMultipleLocksRequest(
                    tileId = associateMultipleLocksData.tileId,
                    siteId = associateMultipleLocksData.siteId,
                    lockIds = associateMultipleLocksData.lockIds
                )
            },
            callback = callback
        )
    }
}

actual fun tiles(): TilesApi = TilesApi