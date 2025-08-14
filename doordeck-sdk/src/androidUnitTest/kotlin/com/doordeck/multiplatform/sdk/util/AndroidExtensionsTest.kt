package com.doordeck.multiplatform.sdk.util

import kotlinx.coroutines.test.runTest
import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidExtensionsTest {

    @Test
    fun shouldGetNumberOfSecondsInDuration() = runTest {
        // Given
        val duration = Duration.parse("P2DT3H4M")

        // When
        val seconds = duration.toWholeSeconds()

        // Then
        assertEquals(183840, seconds)
    }
}