package com.doordeck.multiplatform.sdk.clock

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.model.responses.BasicServerTimeResponse
import com.doordeck.multiplatform.sdk.respondContent
import com.doordeck.multiplatform.sdk.setupMockClient
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime

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
        val serverEpochMilliseconds = (Clock.System.now() + expectedSkew).toEpochMilliseconds()
        val client = CloudHttpClient.setupMockClient(BasicServerTimeResponse(now = serverEpochMilliseconds))

        client.use {
            // When
            ServerTimeSynchronizer.synchronize()

            // Then
            val skew = SystemClock.getSkew()
            assertTrue((skew - expectedSkew).absoluteValue < 5.seconds,
                "Expected ~$expectedSkew skew but was $skew")
        }
    }

    @Test
    fun shouldComputeNegativeSkewWhenDeviceIsAhead() = runTest {
        // Given
        val expectedSkew = (-1).hours
        val serverEpochMilliseconds = (Clock.System.now() + expectedSkew).toEpochMilliseconds()
        val client = CloudHttpClient.setupMockClient(BasicServerTimeResponse(now = serverEpochMilliseconds))

        client.use {
            // When
            ServerTimeSynchronizer.synchronize()

            // Then
            val skew = SystemClock.getSkew()
            assertTrue((skew - expectedSkew).absoluteValue < 5.seconds,
                "Expected ~$expectedSkew skew but was $skew")
        }
    }

    @Test
    fun shouldKeepPreviousSkewWhenSynchronizationFails() = runTest {
        // Given
        SystemClock.setSkew(2.hours)
        val client = CloudHttpClient.setupMockClient<BasicServerTimeResponse?>(null)

        client.use {
            // When
            ServerTimeSynchronizer.synchronize()

            // Then
            assertTrue((SystemClock.getSkew() - 2.hours).absoluteValue < 5.seconds)
        }
    }

    @Test
    fun shouldWaitForFirstSynchronizationWhenInitializing() = runTest {
        // Given
        val expectedSkew = 1.hours
        val serverEpochMilliseconds = (Clock.System.now() + expectedSkew).toEpochMilliseconds()
        val client = CloudHttpClient.setupMockClient(BasicServerTimeResponse(now = serverEpochMilliseconds))

        client.use {
            // When
            withContext(Dispatchers.Default) {
                ServerTimeSynchronizer.initialize()
            }

            // Then
            val skew = SystemClock.getSkew()
            assertTrue((skew - expectedSkew).absoluteValue < 5.seconds,
                "Expected ~$expectedSkew skew but was $skew")
        }
    }

    @Test
    fun shouldStopWaitingForFirstSynchronizationAfterDeadline() = runTest {
        // Given
        val serverEpochMilliseconds = (Clock.System.now() + 1.hours).toEpochMilliseconds()
        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                delay(2.seconds)
                respondContent(BasicServerTimeResponse(now = serverEpochMilliseconds))
            }
        })

        client.use {
            // When
            val elapsed = withContext(Dispatchers.Default) {
                measureTime { ServerTimeSynchronizer.initialize(firstSyncTimeout = 100.milliseconds) }
            }

            // Then
            assertTrue(elapsed < 2.seconds, "Expected to stop waiting after the deadline but it took $elapsed")
            assertEquals(Duration.ZERO, SystemClock.getSkew())
        }
    }
}