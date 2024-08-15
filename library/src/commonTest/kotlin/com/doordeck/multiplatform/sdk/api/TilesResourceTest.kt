package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.SystemTest
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TilesResourceTest : SystemTest() {

    @Test
    fun shouldTestTiles() = runTest {
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