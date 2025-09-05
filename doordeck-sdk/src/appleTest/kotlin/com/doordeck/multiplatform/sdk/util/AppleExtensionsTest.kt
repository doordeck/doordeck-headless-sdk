package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.randomUri
import com.doordeck.multiplatform.sdk.randomUuid
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toNSTimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock.System.now

class AppleExtensionsTest {

    @Test
    fun shouldConvertStringToNsUuid() = runTest {
        // Given
        val uuid = randomUuid()

        // When
        val result = uuid.UUIDString.toNsUuid()

        // Then
        assertEquals(uuid, result)
    }

    @Test
    fun shouldConvertStringToNsUrlComponentes() = runTest {
        // Given
        val url = randomUri()

        // When
        val result = url.toUrlString().toNsUrlComponents()

        // Then
        assertEquals(url, result)
    }

    @Test
    fun shouldConvertStringToNsTimezone() = runTest {
        // Given
        val timezone = TimeZone.of("Europe/Madrid")

        // When
        val result = timezone.toNSTimeZone()

        // Then
        assertEquals(timezone.id, result.name)
    }

    @Test
    fun shouldConvertStringToNsTimeComponents() = runTest {
        // Given
        val time = "18:00"

        // When
        val result = time.toNsTimeComponents()

        // Then
        assertEquals(time, result.toTimeString())
    }

    @Test
    fun shouldConvertStringToNsDateComponents() = runTest {
        // Given
        val date = "2024-07-16"

        // When
        val result = date.toNsDateComponents()

        // Then
        assertEquals(date, result.toDateString())
    }

    @Test
    fun shouldConvertStringToNsDate() = runTest {
        // Given
        val date = now().epochSeconds

        // When
        val result = date.toString().toNsDate()

        // Then
        assertEquals(date, result.toEpochSeconds())
    }

    @Test
    fun shouldConvertDoubleToNsDate() = runTest {
        // Given
        val date = now().epochSeconds

        // When
        val result = date.toDouble().toNsDate()

        // Then
        assertEquals(date, result.toEpochSeconds())
    }
}