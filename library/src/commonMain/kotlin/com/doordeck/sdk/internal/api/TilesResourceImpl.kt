package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.TilesResource
import io.ktor.client.*

class TilesResourceImpl(
    private val httpClient: HttpClient
) : TilesResource {

    override fun getLocksBelongingToTile(tileId: String) {
        TODO("Not yet implemented")
    }

    override fun associateTileWithLock(tileId: String, deviceId: String) {
        TODO("Not yet implemented")
    }

    override fun disassociateTileFromLock(tileId: String, deviceId: String) {
        TODO("Not yet implemented")
    }

    override fun associateMultipleLocks(tileId: String) {
        TODO("Not yet implemented")
    }
}