package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.util.now
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertTrue

class HelperApiAsyncTest : IntegrationTest() {

    @Test
    fun shouldGetServerTimeAsync() = runTest {
        // When
        val response = HelperApi.serverTimeAsync().await()

        // Then
        assertTrue {
            Duration.between(response.now, now()).seconds < 5
        }
    }
}