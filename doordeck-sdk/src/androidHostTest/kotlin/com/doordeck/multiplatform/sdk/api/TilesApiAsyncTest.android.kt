package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_TILE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.exceptions.NotFoundException
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class TilesApiAsyncTest : IntegrationTest() {

    @Test
    fun shouldGetLocksBelongingToTileAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val locks = TilesApi.getLocksBelongingToTileAsync(PLATFORM_TEST_MAIN_TILE_ID).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_SITE_ID, locks.siteId)
        assertEquals(PLATFORM_TEST_MAIN_TILE_ID, locks.tileId)
        assertTrue { locks.deviceIds.contains(PLATFORM_TEST_MAIN_LOCK_ID) }
    }

    @Test
    fun shouldAssociateAndDissociateLockFromTileAsync() = runTest {
        // Given - associate
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        TilesApi.associateMultipleLocksAsync(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID, PLATFORM_TEST_MAIN_SITE_ID, listOf(PLATFORM_TEST_MAIN_LOCK_ID)).await()

        // Then
        val locks = TilesApi.getLocksBelongingToTileAsync(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID).await()
        assertTrue { locks.deviceIds.contains(PLATFORM_TEST_MAIN_LOCK_ID) }

        // Given - dissociate
        TilesApi.associateMultipleLocksAsync(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID, PLATFORM_TEST_MAIN_SITE_ID, emptyList()).await()

        // Then
        val exception = assertFails {
            TilesApi.getLocksBelongingToTileAsync(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID).await()
        }
        assertTrue { exception is NotFoundException }
    }
}