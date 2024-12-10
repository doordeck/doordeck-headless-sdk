package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.internal.api.TilesClient
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TilesResourceImplTest {

    private val tiles = TilesResourceImpl(TilesClient(TEST_HTTP_CLIENT))

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        tiles.getLocksBelongingToTile(DEFAULT_TILE_ID)
    }

    @Test
    fun shouldGetLocksBelongingToTileAsync() = runTest {
        tiles.getLocksBelongingToTileAsync(DEFAULT_TILE_ID).await()
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        tiles.associateMultipleLocks(DEFAULT_TILE_ID, "", emptyList())
    }

    @Test
    fun shouldAssociateMultipleLocksAsync() = runTest {
        tiles.associateMultipleLocksAsync(DEFAULT_TILE_ID, "", emptyList()).await()
    }
}