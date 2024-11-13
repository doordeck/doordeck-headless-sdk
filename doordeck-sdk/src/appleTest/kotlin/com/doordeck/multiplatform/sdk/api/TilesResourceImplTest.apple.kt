package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TilesResourceImplTest {

    private val tiles = TilesResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        tiles.getLocksBelongingToTile(DEFAULT_TILE_ID)
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        tiles.associateMultipleLocks(DEFAULT_TILE_ID, "", emptyList())
    }
}