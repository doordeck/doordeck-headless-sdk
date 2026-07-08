package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.randomUri
import com.doordeck.multiplatform.sdk.randomUuid
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toNSDate
import kotlinx.datetime.toNSTimeZone
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSTimeInterval
import platform.Foundation.NSTimeZone
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.timeZoneWithName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs
import kotlin.time.Clock.System.now

class AppleExtensionsTest {

    @Test
    fun shouldMapStringToNsUuid() = runTest {
        // Given
        val uuid = randomUuid()

        // When
        val result = uuid.UUIDString.toNsUuid()

        // Then
        assertEquals(uuid, result)
    }

    @Test
    fun shouldFailToMapStringToNsUuid() = runTest {
        // Given
        val wrong = "wrong"

        // When
        val exception = assertFails {
            wrong.toNsUuid()
        }

        // Then
        assertIs<NullPointerException>(exception)
    }

    @Test
    fun shouldMapNsUrlComponentsToUrlString() = runTest {
        // Given
        val url = randomUri().toString()

        // When
        val result = url.toNsUrlComponents().toUrlString()

        // Then
        assertEquals(url, result)
    }

    @Test
    fun shouldMapStringToNsUrlComponents() = runTest {
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
    fun shouldMapNsTimeIntervalToWholeSeconds() = runTest {
        // Given
        val interval: NSTimeInterval = 90.75

        // When
        val result = interval.toWholeSeconds()

        // Then
        assertEquals(90, result)
    }

    @Test
    fun shouldMapDoubleToNsDate() = runTest {
        // Given
        val date = now().epochSeconds

        // When
        val result = date.toDouble().toNsDate()

        // Then
        assertEquals(date, result.toEpochSeconds())
    }

    @Test
    fun shouldMapStringToNsDate() = runTest {
        // Given
        val date = now().epochSeconds

        // When
        val result = date.toString().toNsDate()

        // Then
        assertEquals(date, result.toEpochSeconds())
    }

    @Test
    fun shouldMapLongEpochSecondToNsDate() = runTest {
        // Given
        val date = now()

        // When
        val result = date.epochSeconds.epochSecondToNsDate()

        // Then
        assertEquals(date.toNSDate().timeIntervalSince1970.toLong(),
            result.timeIntervalSince1970.toLong())
    }

    @Test
    fun shouldMapIsoStringToNsDate() = runTest {
        // Given
        val isoString = "2023-01-01T00:00:00Z"
        val calendar = NSCalendar.currentCalendar.apply {
            timeZone = NSTimeZone.timeZoneWithName("UTC")!!
        }
        val components = NSDateComponents().apply {
            year = 2023
            month = 1
            day = 1
            hour = 0
            minute = 0
            second = 0
        }
        val date = calendar.dateFromComponents(components)

        // When
        val result = isoString.isoToNsDate()

        // Then
        assertEquals(date, result)
    }

    @Test
    fun shouldFailToMapIsoStringToNsDate() = runTest {
        // Given
        val isoString = "wrong"

        // When
        val exception = assertFails {
            isoString.isoToNsDate()
        }

        // Then
        assertIs<Exception>(exception) // InstantFormatException
    }
}