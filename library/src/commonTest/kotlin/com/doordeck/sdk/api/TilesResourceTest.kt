package com.doordeck.sdk.api

import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.TilesResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test

class TilesResourceTest : SystemTest() {

    private val resource = TilesResourceImpl(createHttpClient(ApiEnvironment.DEV, TEST_AUTH_TOKEN, null))

    @Test
    fun shouldTestTiles() = runBlocking {
        shouldGetLocksBelongingToTile()
        //shouldAssociateMultipleLocks()
    }

    private fun shouldGetLocksBelongingToTile() {
        resource.getLocksBelongingToTile(TEST_MAIN_TILE_ID)
    }

    private fun shouldAssociateMultipleLocks() {
        resource.associateMultipleLocks("", "", arrayOf())
    }
}