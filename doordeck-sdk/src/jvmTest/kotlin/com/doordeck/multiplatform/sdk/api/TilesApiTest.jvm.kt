package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TILE_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.randomUUID
import com.doordeck.multiplatform.sdk.util.toUUID
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TilesApiTest : MockTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        val response = TilesApi.getLocksBelongingToTile(DEFAULT_TILE_ID.toUUID())
        assertEquals(TILE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksBelongingToTileAsync() = runTest {
        val response = TilesApi.getLocksBelongingToTileAsync(DEFAULT_TILE_ID.toUUID()).await()
        assertEquals(TILE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        TilesApi.associateMultipleLocks(DEFAULT_TILE_ID.toUUID(), randomUUID(), emptyList())
    }

    @Test
    fun shouldAssociateMultipleLocksAsync() = runTest {
        TilesApi.associateMultipleLocksAsync(DEFAULT_TILE_ID.toUUID(), randomUUID(), emptyList()).await()
    }
}