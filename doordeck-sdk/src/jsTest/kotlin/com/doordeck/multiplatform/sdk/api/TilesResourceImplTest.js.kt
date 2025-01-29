package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TILE_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TilesResourceImplTest : MockTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        val response = TilesResourceImpl.getLocksBelongingToTile(DEFAULT_TILE_ID).await()
        assertEquals(TILE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        TilesResourceImpl.associateMultipleLocks(DEFAULT_TILE_ID, "", emptyList()).await()
    }
}