package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_TILE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.exceptions.NotFoundException
import com.doordeck.multiplatform.sdk.model.data.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.model.data.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.responses.BasicTileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TilesApiTest : CallbackTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // When
        val result = callbackApiCall<ResultData<BasicTileLocksResponse>> {
            TilesApi.getLocksBelongingToTile(
                data = GetLocksBelongingToTileData(PLATFORM_TEST_MAIN_TILE_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertNotNull(result.success)
        assertNotNull(result.success.result)
        assertEquals(PLATFORM_TEST_MAIN_SITE_ID, result.success.result.siteId)
        assertEquals(PLATFORM_TEST_MAIN_TILE_ID, result.success.result.tileId)
        assertTrue { result.success.result.deviceIds.contains(PLATFORM_TEST_MAIN_LOCK_ID) }
    }

    @Test
    fun shouldAssociateAndDissociateLockFromTile() = runTest {
        // Given - associate
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // When
        callbackApiCall<ResultData<Unit>> {
            TilesApi.associateMultipleLocks(
                data = AssociateMultipleLocksData(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID, PLATFORM_TEST_MAIN_SITE_ID, listOf(PLATFORM_TEST_MAIN_LOCK_ID)).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        val locks = callbackApiCall<ResultData<BasicTileLocksResponse>> {
            TilesApi.getLocksBelongingToTile(
                data = GetLocksBelongingToTileData(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(locks.success)
        assertNotNull(locks.success.result)
        assertContains(locks.success.result.deviceIds, PLATFORM_TEST_MAIN_LOCK_ID)

        // Given - dissociate
        callbackApiCall<ResultData<Unit>> {
            TilesApi.associateMultipleLocks(
                data = AssociateMultipleLocksData(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID, PLATFORM_TEST_MAIN_SITE_ID, emptyList()).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        val result = callbackApiCall<ResultData<BasicTileLocksResponse>> {
            TilesApi.getLocksBelongingToTile(
                data = GetLocksBelongingToTileData(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(result.failure)
        assertContains(result.failure.exceptionType, NotFoundException::class.simpleName!!)
    }
}