package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TILE_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.api.model.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.api.model.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TilesResourceImplTest : MockTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        val response = TilesResourceImpl.getLocksBelongingToTile(DEFAULT_TILE_ID)
        assertEquals(TILE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksBelongingToTileJson() = runTest {
        val response = TilesResourceImpl.getLocksBelongingToTileJson(GetLocksBelongingToTileData(DEFAULT_TILE_ID).toJson())
        assertEquals(TILE_LOCKS_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        TilesResourceImpl.associateMultipleLocks(DEFAULT_TILE_ID, "", emptyList())
    }

    @Test
    fun shouldAssociateMultipleLocksJson() = runTest {
        TilesResourceImpl.associateMultipleLocksJson(AssociateMultipleLocksData(DEFAULT_TILE_ID, "", emptyList()).toJson())
    }
}