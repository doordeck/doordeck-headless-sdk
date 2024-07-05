package com.doordeck.sdk.api

import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.TilesResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class TilesResourceTest : SystemTest() {

    private val resource = TilesResourceImpl(createHttpClient(TEST_ENVIRONMENT, TEST_AUTH_TOKEN, null))

    @Test
    fun shouldTestTiles() = runBlocking {
        shouldGetLocksBelongingToTile()
        //shouldAssociateMultipleLocks()
    }

    private fun shouldGetLocksBelongingToTile() {
        // When
        val locks = resource.getLocksBelongingToTile(TEST_MAIN_TILE_ID)

        // Then
        assertTrue { locks.deviceIds.isNotEmpty() }
    }

    private fun shouldAssociateMultipleLocks() {
        resource.associateMultipleLocks("", "", arrayOf())
    }
}