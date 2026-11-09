package com.doordeck.multiplatform.sdk.clock

import com.doordeck.multiplatform.sdk.IntegrationTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

class SystemClockTest : IntegrationTest() {

    @Test
    fun shouldDefaultToNoSkew() = runTest {
        // When
        val result = SystemClock.getSkew()

        // Then
        assertEquals(Duration.ZERO, result)
        val delta = SystemClock.now() - Clock.System.now()
        assertTrue(delta.absoluteValue < 2.seconds)
    }

    @Test
    fun shouldApplyPositiveSkew() = runTest {
        // Given
        SystemClock.setSkew(3.hours)

        // When
        val delta = SystemClock.now() - Clock.System.now()

        // Then
        assertTrue((delta - 3.hours).absoluteValue < 2.seconds)
        assertEquals(3.hours, SystemClock.getSkew())
    }

    @Test
    fun shouldApplyNegativeSkew() = runTest {
        // Given
        SystemClock.setSkew((-3).hours)

        // When
        val delta = SystemClock.now() - Clock.System.now()

        // Then
        assertTrue((delta + 3.hours).absoluteValue < 2.seconds)
    }

    @Test
    fun shouldResetSkew() = runTest {
        // Given
        SystemClock.setSkew(3.hours)

        // When
        SystemClock.reset()

        // Then
        assertEquals(Duration.ZERO, SystemClock.getSkew())
    }
}
