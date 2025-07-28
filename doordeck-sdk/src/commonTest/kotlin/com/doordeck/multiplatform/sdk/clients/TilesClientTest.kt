package com.doordeck.multiplatform.sdk.clients
/*
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_TILE_ID
import com.doordeck.multiplatform.sdk.exceptions.NotFoundException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class TilesClientTest : IntegrationTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val locks = TilesClient.getLocksBelongingToTileRequest(TEST_MAIN_TILE_ID)

        // Then
        assertTrue { locks.deviceIds.contains(TEST_MAIN_LOCK_ID) }
    }

    @Test
    fun shouldAssociateAndDissociateLockFromTile() = runTest {
        // Given - associate
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        TilesClient.associateMultipleLocksRequest(TEST_SUPPLEMENTARY_TILE_ID, TEST_MAIN_SITE_ID, listOf(TEST_MAIN_LOCK_ID))

        // Then
        val locks = TilesClient.getLocksBelongingToTileRequest(TEST_SUPPLEMENTARY_TILE_ID)
        assertTrue { locks.deviceIds.contains(TEST_MAIN_LOCK_ID) }

        // Given - dissociate
        TilesClient.associateMultipleLocksRequest(TEST_SUPPLEMENTARY_TILE_ID, TEST_MAIN_SITE_ID, emptyList())

        // Then
        val exception = assertFails {
            TilesClient.getLocksBelongingToTileRequest(TEST_SUPPLEMENTARY_TILE_ID)
        }
        assertTrue { exception is NotFoundException }
    }
}
 */