package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.SystemTest
import com.doordeck.multiplatform.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class TilesResourceTest : SystemTest() {

    @Test
    fun shouldTestTiles() = runBlocking {
        shouldGetLocksBelongingToTile()
        //shouldAssociateMultipleLocks()
    }

    private fun shouldGetLocksBelongingToTile() {
        // When
        val locks = TILES_RESOURCE.getLocksBelongingToTile(TEST_MAIN_TILE_ID)

        // Then
        assertTrue { locks.deviceIds.isNotEmpty() }
    }

    private fun shouldAssociateMultipleLocks() {
        TILES_RESOURCE.associateMultipleLocks("", "", arrayOf())
    }
}