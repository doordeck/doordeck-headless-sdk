package com.doordeck.multiplatform.sdk.clock

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.model.responses.BasicServerTimeResponse
import com.doordeck.multiplatform.sdk.respondContent
import com.doordeck.multiplatform.sdk.setupMockClient
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime

class ServerTimeSynchronizerTest : IntegrationTest() {

    @Test
    fun shouldComputePositiveSkewWhenDeviceIsBehind() = runTest {
        // Given
        val expectedSkew = 1.hours
        val serverEpochMilliseconds = (Clock.System.now() + expectedSkew).toEpochMilliseconds()
        CloudHttpClient.setupMockClient(BasicServerTimeResponse(now = serverEpochMilliseconds))

        // When
        ServerTimeSynchronizer.synchronize()

        // Then
        val skew = SystemClock.getSkew()
        assertTrue((skew - expectedSkew).absoluteValue < 5.seconds, "Expected ~$expectedSkew skew but was $skew")
    }

    @Test
    fun shouldComputeNegativeSkewWhenDeviceIsAhead() = runTest {
        // Given
        val expectedSkew = (-1).hours
        val serverEpochMilliseconds = (Clock.System.now() + expectedSkew).toEpochMilliseconds()
        CloudHttpClient.setupMockClient(BasicServerTimeResponse(now = serverEpochMilliseconds))

        // When
        ServerTimeSynchronizer.synchronize()

        // Then
        val skew = SystemClock.getSkew()
        assertTrue((skew - expectedSkew).absoluteValue < 5.seconds, "Expected ~$expectedSkew skew but was $skew")
    }

    @Test
    fun shouldKeepPreviousSkewWhenSynchronizationFails() = runTest {
        // Given
        SystemClock.setSkew(2.hours)
        CloudHttpClient.setupMockClient<BasicServerTimeResponse?>(null)

        // When
        ServerTimeSynchronizer.synchronize()

        // Then
        assertEquals(2.hours, SystemClock.getSkew())
    }

    @Test
    fun shouldWaitForFirstSynchronizationWhenInitializing() = runTest {
        // Given
        val expectedSkew = 1.hours
        val serverEpochMilliseconds = (Clock.System.now() + expectedSkew).toEpochMilliseconds()
        CloudHttpClient.setupMockClient(BasicServerTimeResponse(now = serverEpochMilliseconds))

        // When
        ServerTimeSynchronizer.initialize()

        // Then
        val skew = SystemClock.getSkew()
        assertTrue((skew - expectedSkew).absoluteValue < 5.seconds, "Expected ~$expectedSkew skew but was $skew")
    }

    @Test
    fun shouldStopWaitingForFirstSynchronizationAfterDeadline() = runTest {
        // Given
        val serverEpochMilliseconds = (Clock.System.now() + 1.hours).toEpochMilliseconds()
        CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                delay(2.seconds)
                respondContent(BasicServerTimeResponse(now = serverEpochMilliseconds))
            }
        })

        // When
        val elapsed = measureTime { ServerTimeSynchronizer.initialize(firstSyncTimeout = 100.milliseconds) }

        // Then
        assertTrue(elapsed < 2.seconds, "Expected to stop waiting after the deadline but it took $elapsed")
        assertEquals(Duration.ZERO, SystemClock.getSkew())
    }

    @Test
    fun shouldNotPublishSkewAfterStopping() = runTest {
        // Given - a synchronization that is still in flight when the loop is stopped
        val serverEpochMilliseconds = (Clock.System.now() + 1.hours).toEpochMilliseconds()
        CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                delay(1.seconds)
                respondContent(BasicServerTimeResponse(now = serverEpochMilliseconds))
            }
        })
        ServerTimeSynchronizer.initialize(firstSyncTimeout = 100.milliseconds)

        // When
        ServerTimeSynchronizer.stop()
        SystemClock.reset()
        delay(2.seconds)

        // Then
        assertEquals(Duration.ZERO, SystemClock.getSkew())
    }
}
