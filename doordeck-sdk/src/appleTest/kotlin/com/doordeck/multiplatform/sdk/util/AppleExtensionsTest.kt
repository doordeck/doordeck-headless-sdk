package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.randomUri
import com.doordeck.multiplatform.sdk.randomUuid
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toNSTimeZone
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarIdentifierBuddhist
import platform.Foundation.NSCalendarIdentifierGregorian
import platform.Foundation.NSCalendarIdentifierJapanese
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeInterval
import platform.Foundation.NSTimeZone
import platform.Foundation.timeZoneWithName
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.time.Clock

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

        // When/Then
        assertFailsWith<NullPointerException> {
            wrong.toNsUuid()
        }
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
    fun shouldResolveDateIndependentlyOfTheDeviceCalendar() = runTest {
        // Given
        val date = "2024-07-16"
        val gregorianUtcFormatter = NSDateFormatter().apply {
            dateFormat = "yyyy-MM-dd"
            locale = NSLocale(localeIdentifier = "en_US_POSIX")
            calendar = NSCalendar(calendarIdentifier = NSCalendarIdentifierGregorian)
            timeZone = NSTimeZone.timeZoneWithName("UTC")!!
        }
        val instant = gregorianUtcFormatter.dateFromString(date)!!

        // When
        val yearOf = { identifier: String ->
            NSCalendar(calendarIdentifier = identifier).apply {
                timeZone = NSTimeZone.timeZoneWithName("UTC")!!
            }.components(
                unitFlags = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
                fromDate = instant
            ).year
        }

        // Then
        assertEquals(2567L, yearOf(NSCalendarIdentifierBuddhist!!).toLong())
        assertEquals(6L, yearOf(NSCalendarIdentifierJapanese!!).toLong())
        assertNotEquals(2024L, yearOf(NSCalendarIdentifierBuddhist!!).toLong())

        assertEquals(2024L, date.toNsDateComponents().year.toLong())
        assertEquals(date, date.toNsDateComponents().toDateString())
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
        val date = Clock.System.now().epochSeconds

        // When
        val result = date.toDouble().toNsDate()

        // Then
        assertEquals(date, result.toEpochSeconds())
    }

    @Test
    fun shouldMapStringToNsDate() = runTest {
        // Given
        val date = Clock.System.now().epochSeconds

        // When
        val result = date.toString().toNsDate()

        // Then
        assertEquals(date, result.toEpochSeconds())
    }

    @Test
    fun shouldMapLongEpochMillisecondToNsDate() = runTest {
        // Given
        val date = Clock.System.now()

        // When
        val result = date.toEpochMilliseconds().epochMillisecondToNsDate()

        // Then
        assertTrue {
            abs(date.toEpochMilliseconds() -
                    result.toKotlinInstant().toEpochMilliseconds()) <= 1
        }
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

        // When/Then
        assertFailsWith<Exception> {
            isoString.isoToNsDate()
        }
    }
}