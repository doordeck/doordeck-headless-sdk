package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.api.model.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.api.model.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.internal.api.TilesClient
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TilesResourceImplTest {

    private val tiles = TilesResourceImpl(TilesClient(TEST_HTTP_CLIENT))

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        tiles.getLocksBelongingToTile(DEFAULT_TILE_ID)
    }

    @Test
    fun shouldGetLocksBelongingToTileJson() = runTest {
        tiles.getLocksBelongingToTileJson(GetLocksBelongingToTileData(DEFAULT_TILE_ID).toJson())
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        tiles.associateMultipleLocks(DEFAULT_TILE_ID, "", emptyList())
    }

    @Test
    fun shouldAssociateMultipleLocksJson() = runTest {
        tiles.associateMultipleLocksJson(AssociateMultipleLocksData(DEFAULT_TILE_ID, "", emptyList()).toJson())
    }
}