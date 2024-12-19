package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TilesResourceImplTest : MockTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        TilesResourceImpl.getLocksBelongingToTile(DEFAULT_TILE_ID)
    }

    @Test
    fun shouldGetLocksBelongingToTileAsync() = runTest {
        TilesResourceImpl.getLocksBelongingToTileAsync(DEFAULT_TILE_ID).await()
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        TilesResourceImpl.associateMultipleLocks(DEFAULT_TILE_ID, "", emptyList())
    }

    @Test
    fun shouldAssociateMultipleLocksAsync() = runTest {
        TilesResourceImpl.associateMultipleLocksAsync(DEFAULT_TILE_ID, "", emptyList()).await()
    }
}