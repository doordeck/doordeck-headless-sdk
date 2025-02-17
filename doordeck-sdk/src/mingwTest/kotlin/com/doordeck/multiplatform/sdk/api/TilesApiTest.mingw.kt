package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TILE_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.model.data.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.model.data.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TilesApiTest : MockTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        val response = TilesApi.getLocksBelongingToTile(DEFAULT_TILE_ID)
        assertEquals(TILE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksBelongingToTileJson() = runTest {
        val response = TilesApi.getLocksBelongingToTileJson(GetLocksBelongingToTileData(DEFAULT_TILE_ID).toJson())
        assertEquals(TILE_LOCKS_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        TilesApi.associateMultipleLocks(DEFAULT_TILE_ID, "", emptyList())
    }

    @Test
    fun shouldAssociateMultipleLocksJson() = runTest {
        TilesApi.associateMultipleLocksJson(AssociateMultipleLocksData(DEFAULT_TILE_ID, "", emptyList()).toJson())
    }
}