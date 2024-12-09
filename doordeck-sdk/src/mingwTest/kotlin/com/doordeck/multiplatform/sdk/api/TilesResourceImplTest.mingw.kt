package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.TEST_MOCK_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.api.model.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.api.model.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TilesResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        CloudHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
    }

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        TilesResourceImpl.getLocksBelongingToTile(DEFAULT_TILE_ID)
    }

    @Test
    fun shouldGetLocksBelongingToTileJson() = runTest {
        TilesResourceImpl.getLocksBelongingToTileJson(GetLocksBelongingToTileData(DEFAULT_TILE_ID).toJson())
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