package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TILE_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.model.data.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.model.data.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TilesApiTest : CallbackTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        callbackTest(
            apiCall = {
                TilesApi.getLocksBelongingToTile(
                    data = GetLocksBelongingToTileData(DEFAULT_TILE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = TILE_LOCKS_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldAssociateMultipleLocks() = runTest {
        callbackTest(
            apiCall = {
                TilesApi.associateMultipleLocks(
                    data = AssociateMultipleLocksData(DEFAULT_TILE_ID, "", emptyList()).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }
}