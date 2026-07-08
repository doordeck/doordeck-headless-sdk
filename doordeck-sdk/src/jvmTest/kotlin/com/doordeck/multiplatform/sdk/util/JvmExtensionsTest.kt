package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import java.net.InetAddress
import java.net.URI
import java.net.UnknownHostException
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeParseException
import java.time.zone.ZoneRulesException
import java.util.EnumSet
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class JvmExtensionsTest {

    @Test
    fun shouldGetNumberOfSecondsInDuration() = runTest {
        // Given
        val duration = Duration.parse("P2DT3H4M")

        // When
        val seconds = duration.toWholeSeconds()

        // Then
        assertEquals(183840, seconds)
    }

    @Test
    fun shouldMapStringToLocalTime12() = runTest {
        // Given
        val localTime = "07:34"

        // When
        val result = localTime.toLocalTime()

        // Then
        assertEquals(LocalTime.of(7, 34), result)
    }

    @Test
    fun shouldMapStringToLocalTime24() = runTest {
        // Given
        val localTime = "16:34"

        // When
        val result = localTime.toLocalTime()

        // Then
        assertEquals(LocalTime.of(16, 34), result)
    }

    @Test
    fun shouldFailToMapStringToLocalTime() = runTest {
        // Given
        val wrong = "localtime"

        // When
        val exception = assertFailsWith<DateTimeParseException> {
            wrong.toLocalTime()
        }

        // Then
        assertEquals("Text 'localtime' could not be parsed at index 0", exception.message)
    }

    @Test
    fun shouldMapLocalTimeToLocalTimeString12() = runTest {
        // Given
        val localTime = LocalTime.of(7, 34)

        // When
        val result = localTime.toLocalTimeString()

        // Then
        assertEquals("07:34", result)
    }

    @Test
    fun shouldMapLocalTimeToLocalTimeString24() = runTest {
        // Given
        val localTime = LocalTime.of(16, 34)

        // When
        val result = localTime.toLocalTimeString()

        // Then
        assertEquals("16:34", result)
    }

    @Test
    fun shouldMapStringToLocalDate() = runTest {
        // Given
        val localDate = "2023-10-27"

        // When
        val result = localDate.toLocalDate()

        // Then
        assertEquals(LocalDate.of(2023, 10, 27), result)
    }

    @Test
    fun shouldFailToMapStringToLocalDate() = runTest {
        // Given
        val wrong = "localdate"

        // When
        val exception = assertFailsWith<DateTimeParseException> {
            wrong.toLocalDate()
        }

        // Then
        assertEquals("Text 'localdate' could not be parsed at index 0", exception.message)
    }

    @Test
    fun shouldMapLocalDateToLocalDateString() = runTest {
        // Given
        val localDate = LocalDate.of(2023, 10, 27)

        // When
        val result = localDate.toLocalDateString()

        // Then
        assertEquals("2023-10-27", result)
    }

    @Test
    fun shouldMapSecondsToDuration() = runTest {
        // Given
        val duration = 120

        // When
        val result = duration.secondsToDuration()

        // Then
        assertEquals(Duration.ofMinutes(2), result)
    }

    @Test
    fun shouldMapStringToUri() = runTest {
        // Given
        val uri = "https://google.com"

        // When
        val result = uri.toUri()

        // Then
        assertEquals(URI.create(uri), result)
    }

    @Test
    fun shouldFailToMapStringToUri() = runTest {
        // Given
        val wrong = "`"

        // When
        val exception = assertFailsWith<IllegalArgumentException> {
            wrong.toUri()
        }

        // Then
        assertEquals("Illegal character in path at index 0: `", exception.message)
    }

    @Test
    fun shouldMapStringToUrl() = runTest {
        // Given
        val url = "https://google.com"

        // When
        val result = url.toUrl()

        // Then
        assertEquals(URI.create(url).toURL(), result)
    }

    @Test
    fun shouldFailToMapStringToUrl() = runTest {
        // Given
        val wrong = "wrong"

        // When
        val exception = assertFailsWith<IllegalArgumentException> {
            wrong.toUrl()
        }

        // Then
        assertEquals("URI is not absolute", exception.message)
    }

    @Test
    fun shouldMapStringToZoneId() = runTest {
        // Given
        val zoneId = "UTC"

        // When
        val result = zoneId.toZoneId()

        // Then
        assertEquals(ZoneId.of("UTC"), result)
    }

    @Test
    fun shouldFailToMapStringToZoneId() = runTest {
        // Given
        val wrong = "wrong"

        // When
        val exception = assertFailsWith<ZoneRulesException> {
            wrong.toZoneId()
        }

        // Then
        assertEquals("Unknown time-zone ID: wrong", exception.message)
    }

    @Test
    fun shouldMapStringToUuid() = runTest {
        // Given
        val uuid = "8abede37-298a-4c70-bbcc-19bddecc53c7"

        // When
        val result = uuid.toUuid()

        // Then
        assertEquals(UUID.fromString(uuid), result)
    }

    @Test
    fun shouldFailToMapStringToUuid() = runTest {
        // Given
        val wrong = "wrong"

        // When
        val exception = assertFailsWith<IllegalArgumentException> {
            wrong.toUuid()
        }

        // Then
        assertEquals("Invalid UUID string: wrong", exception.message)
    }

    @Test
    fun shouldMapStringToInstantWithDot() = runTest {
        // Given
        val instantString = "1672531200.0"

        // When
        val result = instantString.toInstant()

        // Then
        assertEquals(Instant.ofEpochSecond(1672531200L, 0L), result)
    }

    @Test
    fun shouldMapStringToInstantWithoutDot() = runTest {
        // Given
        val instantString = "1672531200"

        // When
        val result = instantString.toInstant()

        // Then
        assertEquals(Instant.ofEpochSecond(1672531200L, 0L), result)
    }

    @Test
    fun shouldFailToMapStringToInstant() = runTest {
        // Given
        val wrong = "wrong"

        // When
        val exception = assertFailsWith<NumberFormatException> {
            wrong.toInstant()
        }

        // Then
        assertEquals("For input string: \"wrong\"", exception.message)
    }

    @Test
    fun shouldMapIsoStringToInstant() = runTest {
        // Given
        val isoString = "2023-01-01T00:00:00Z"

        // When
        val result = isoString.isoToInstant()

        // Then
        assertEquals(Instant.parse(isoString), result)
    }

    @Test
    fun shouldFailToMapIsoStringToInstant() = runTest {
        // Given
        val wrong = "wrong"

        // When
        val exception = assertFailsWith<DateTimeParseException> {
            wrong.isoToInstant()
        }

        // Then
        assertEquals("Text 'wrong' could not be parsed at index 0", exception.message)
    }

    @Test
    fun shouldMapDoubleToInstant() = runTest {
        // Given
        val seconds = 1672531200.0

        // When
        val instant = seconds.toInstant()

        // Then
        assertEquals(Instant.ofEpochSecond(1672531200L), instant)
    }

    @Test
    fun shouldMapStringToInetAddress() = runTest {
        // Given
        val host = "127.0.0.1"

        // When
        val result = host.toInetAddress()

        // Then
        assertEquals(InetAddress.getByName("127.0.0.1"), result)
    }

    @Test
    fun shouldFailToMapStringToInetAddress() = runTest {
        // Given
        val host = "host"

        // When
        val exception = assertFailsWith<UnknownHostException> {
            host.toInetAddress()
        }

        // Then
        assertEquals("host: nodename nor servname provided, or not known", exception.message)
    }

    @Test
    fun shouldMapListToEnumSet() = runTest {
        // Given
        val enums = EnumSet.of(ApiEnvironment.DEV, ApiEnvironment.STAGING,
            ApiEnvironment.PROD)

        // When
        val result = enums.toList().toEnumSet()

        // Then
        assertEquals(enums, result)
    }

    @Test
    fun shouldMapEmptyListToEnumSet() = runTest {
        // Given
        val enums = EnumSet.noneOf(ApiEnvironment::class.java)

        // When
        val result = enums.toList().toEnumSet()

        // Then
        assertEquals(enums, result)
    }

    @Test
    fun shouldCreateCompletableFuture() = runTest {
        // Given
        val toReturn = "result"

        // When
        val result = completableFuture {
            toReturn
        }.await()

        // Then
        assertEquals(toReturn, result)
    }

    @Test
    fun shouldFailToProcessCompletableFuture() = runTest {
        // Given
        val exception = SdkException("Error")

        // When
        val result = completableFuture {
            exception
        }.await()

        // Then
        assertIs<SdkException>(result)
    }
}