package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.util.now
import kotlinx.coroutines.test.runTest
import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertTrue

class HelperApiTest : IntegrationTest() {

    @Test
    fun shouldGetServerTime() = runTest {
        // When
        val response = HelperApi.serverTime()

        // Then
        assertTrue {
            Duration.between(response.now, now()).seconds < 5
        }
    }
}