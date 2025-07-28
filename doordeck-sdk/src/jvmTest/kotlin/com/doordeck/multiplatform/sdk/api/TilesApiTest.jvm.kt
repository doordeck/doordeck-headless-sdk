package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_TILE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.exceptions.NotFoundException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class TilesApiTest : IntegrationTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val locks = TilesApi.getLocksBelongingToTile(PLATFORM_TEST_MAIN_TILE_ID)

        // Then
        assertTrue { locks.deviceIds.contains(PLATFORM_TEST_MAIN_LOCK_ID) }
    }

    @Test
    fun shouldAssociateAndDissociateLockFromTile() = runTest {
        // Given - associate
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        TilesApi.associateMultipleLocks(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID, PLATFORM_TEST_MAIN_SITE_ID, listOf(PLATFORM_TEST_MAIN_LOCK_ID))

        // Then
        val locks = TilesApi.getLocksBelongingToTile(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID)
        assertTrue { locks.deviceIds.contains(PLATFORM_TEST_MAIN_LOCK_ID) }

        // Given - dissociate
        TilesApi.associateMultipleLocks(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID, PLATFORM_TEST_MAIN_SITE_ID, emptyList())

        // Then
        val exception = assertFails {
            TilesApi.getLocksBelongingToTile(PLATFORM_TEST_SUPPLEMENTARY_TILE_ID)
        }
        assertTrue { exception is NotFoundException }
    }
}