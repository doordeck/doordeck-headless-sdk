package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TilesResourceTest : IntegrationTest() {

    @Test
    fun shouldGetLocksBelongingToTile() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val locks = TILES_RESOURCE.getLocksBelongingToTile(TEST_MAIN_TILE_ID).await()

        // Then
        assertTrue { locks.deviceIds.isNotEmpty() }
    }
}