package com.doordeck.multiplatform.sdk.clock

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.model.responses.BasicServerTimeResponse
import com.doordeck.multiplatform.sdk.setupMockClient
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

class ServerTimeSynchronizerTest {

    @AfterTest
    fun tearDown() {
        ServerTimeSynchronizer.stop()
        SystemClock.reset()
    }

    @Test
    fun shouldComputePositiveSkewWhenDeviceIsBehind() = runTest {
        // Given
        val expectedSkew = 1.hours
        val serverEpochSeconds = (Clock.System.now() + expectedSkew).epochSeconds
        CloudHttpClient.setupMockClient(BasicServerTimeResponse(now = serverEpochSeconds))

        // When
        ServerTimeSynchronizer.synchronize()

        // Then
        val skew = SystemClock.getSkew()
        assertTrue((skew - expectedSkew).absoluteValue < 5.seconds,
            "Expected ~$expectedSkew skew but was $skew")
    }

    @Test
    fun shouldComputeNegativeSkewWhenDeviceIsAhead() = runTest {
        // Given
        val expectedSkew = (-1).hours
        val serverEpochSeconds = (Clock.System.now() + expectedSkew).epochSeconds
        CloudHttpClient.setupMockClient(BasicServerTimeResponse(now = serverEpochSeconds))

        // When
        ServerTimeSynchronizer.synchronize()

        // Then
        val skew = SystemClock.getSkew()
        assertTrue((skew - expectedSkew).absoluteValue < 5.seconds,
            "Expected ~$expectedSkew skew but was $skew")
    }

    @Test
    fun shouldKeepPreviousSkewWhenSynchronizationFails() = runTest {
        // Given
        SystemClock.setSkew(2.hours)
        CloudHttpClient.setupMockClient<BasicServerTimeResponse?>(null)

        // When
        ServerTimeSynchronizer.synchronize()

        // Then
        assertTrue((SystemClock.getSkew() - 2.hours).absoluteValue < 5.seconds)
    }
}
