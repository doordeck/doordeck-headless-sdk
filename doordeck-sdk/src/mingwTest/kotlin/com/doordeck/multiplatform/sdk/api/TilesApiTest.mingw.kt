package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_TILE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_TILE_ID
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.exceptions.NotFoundException
import com.doordeck.multiplatform.sdk.model.data.AssociateMultipleLocksData
import com.doordeck.multiplatform.sdk.model.data.GetLocksBelongingToTileData
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.responses.BasicTileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.unwrap
import com.doordeck.multiplatform.sdk.unwrapFailure
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TilesApiTest : CallbackTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val result = callbackApiCall<ResultData<BasicTileLocksResponse>> {
            TilesApi.getLocksBelongingToTile(
                data = GetLocksBelongingToTileData(PLATFORM_TEST_MAIN_TILE_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_SITE_ID, result.siteId)
        assertEquals(PLATFORM_TEST_MAIN_TILE_ID, result.tileId)
        assertTrue { result.deviceIds.contains(PLATFORM_TEST_MAIN_LOCK_ID) }
    }

    @Test
    fun shouldAssociateAndDissociateLockFromTile() = runTest {
        // Given - associate
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        callbackApiCall<ResultData<Unit>> {
            TilesApi.associateMultipleLocks(
                data = AssociateMultipleLocksData(
                    PLATFORM_TEST_SUPPLEMENTARY_TILE_ID,
                    PLATFORM_TEST_MAIN_SITE_ID,
                    listOf(PLATFORM_TEST_MAIN_LOCK_ID)
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        val locks = callbackApiCall<ResultData<BasicTileLocksResponse>> {
            TilesApi.getLocksBelongingToTile(
                data = GetLocksBelongingToTileData(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertContains(locks.deviceIds, PLATFORM_TEST_MAIN_LOCK_ID)

        // Given - dissociate
        callbackApiCall<ResultData<Unit>> {
            TilesApi.associateMultipleLocks(
                data = AssociateMultipleLocksData(
                    PLATFORM_TEST_SUPPLEMENTARY_TILE_ID,
                    PLATFORM_TEST_MAIN_SITE_ID,
                    emptyList()
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        val result = callbackApiCall<ResultData<BasicTileLocksResponse>> {
            TilesApi.getLocksBelongingToTile(
                data = GetLocksBelongingToTileData(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID).toJson(),
                callback = TestCallback
            )
        }.unwrapFailure()
        assertContains(result.exceptionType, NotFoundException::class.simpleName!!)
        assertEquals("API call failed with: No devices associated with this tile", result.exceptionMessage)
    }
}