package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Clock

class HelperApiTest : IntegrationTest() {

    @Test
    fun shouldGetServerTime() = runTest {
        // When
        val response = HelperApi.serverTime().await()

        // Then
        assertTrue {
            abs(response.now - Clock.System.now().epochSeconds) < 5
        }
    }
}