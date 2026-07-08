package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.toNSDate
import platform.Foundation.timeIntervalSinceDate
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Clock

class HelperApiTest : IntegrationTest() {

    @Test
    fun shouldGetServerTime() = runTest {
        // When
        val response = HelperApi.serverTime()

        // Then
        assertTrue {
            abs(response.now.timeIntervalSinceDate(Clock.System.now().toNSDate())) < 5
        }
    }
}