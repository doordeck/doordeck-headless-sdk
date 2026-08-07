package com.doordeck.multiplatform.sdk.tile

import com.doordeck.multiplatform.sdk.exceptions.InvalidTileUrlException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class TileUrlParserTest {

    @Test
    fun dnaQueryStringIsPreservedAndNotExtracted() = runTest {
        // Given
        val uuid = "e2fcd000-8ce3-11f1-9876-d923122ac2fc"
        val input = "https://doordeck.link/$uuid?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)

        // Then
        assertEquals(uuid, result.tileId)
        assertEquals(input, result.url)
    }

    @Test
    fun simpleTileUrl() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val input = "https://doordeck.link/$uuid"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun trailingSlashIsIgnored() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val input = "https://doordeck.link/$uuid/"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun extraPathPrefixSegmentsAreIgnored() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val input = "https://doordeck.link/uuid/$uuid"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun deepNestedPathWithTrailingSlashAndQueryStillFindsLastSegment() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val input = "https://this.thirdparty.com/tile/scan/what/not/$uuid/?uuid=hello"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun nfcWithHttpsPrefixCodeIsDecompressed() = runTest {
        // Given
        val uuid = "e2fcd000-8ce3-11f1-9876-d923122ac2fc"
        val input = "\u0004doordeck.link/$uuid?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.NFC)

        // Then
        assertEquals(uuid, result.tileId)
        assertEquals(
            "https://doordeck.link/$uuid?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07",
            result.url
        )
    }

    @Test
    fun nfcWithUnknownPrefixCodeThrows() = runTest {
        // Given
        val uuid = "e2fcd000-8ce3-11f1-9876-d923122ac2fc"
        val input = "\u0006doordeck.link/$uuid?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.NFC)
        }

        // Then
        assertEquals("Invalid URL format: $input", exception.message)
    }

    @Test
    fun nfcWithEmptyPrefixCodeIsParsed() = runTest {
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val url = "https://doordeck.link/$uuid"
        val input = "\u0000$url"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.NFC)

        // Then
        assertEquals(uuid, result.tileId)
        assertEquals(url, result.url)
    }

    @Test
    fun nfcWithFullUrlDoesNotDecompress() = runTest {
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val input = "https://doordeck.link/$uuid"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.NFC)

        // Then
        assertEquals(uuid, result.tileId)
        assertEquals(input, result.url)
    }

    @Test
    fun nfcWithoutPrefixCodeThrows() = runTest {
        // Given
        val uuid = "e2fcd000-8ce3-11f1-9876-d923122ac2fc"
        val input = "doordeck.link/$uuid?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.NFC)
        }

        // Then
        assertEquals("Invalid URL format: $input", exception.message)
    }

    @Test
    fun bareUuidThrows() = runTest {
        // Given
        val input = "0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("Invalid URL format: $input", exception.message)
    }

    @Test
    fun nonUuidLastSegmentThrows() = runTest {
        // Given
        val input = "https://doordeck.link/not-a-uuid"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("Last path segment: not-a-uuid, is not a valid tile UUID", exception.message)
    }

    @Test
    fun noPathSegmentsThrows() = runTest {
        // Given
        val input = "https://doordeck.link/"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("No path segments found in: $input", exception.message)
    }

    @Test
    fun extraTrailingSegmentAfterUuidThrows() = runTest {
        // Given
        val input = "https://doordeck.link/0c019ad0-38d4-11f1-8662-339ef0f86a15/extra-segment"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("Last path segment: extra-segment, is not a valid tile UUID", exception.message)
    }

    @Test
    fun uuidInQueryStringIsNeverUsedAsTileId() = runTest {
        // Given
        val input = "https://doordeck.link/?uuid=0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("No path segments found in: $input", exception.message)
    }

    @Test
    fun emptyInputShouldThrow() = runTest {
        // Given
        val input = ""

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("Input is empty", exception.message)
    }

    @Test
    fun mixedCaseUuidInPathIsPreservedExactly() = runTest {
        // Given
        val uuid = "E2fcD000-8ce3-11F1-9876-d923122AC2fc"
        val input = "https://doordeck.link/$uuid"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun fullyUppercaseUuidInPathIsPreserved() = runTest {
        // Given
        val uuid = "E2FCD000-8CE3-11F1-9876-D923122AC2FC"
        val input = "https://doordeck.link/$uuid"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun nfcDecompressionDoesNotAlterUuidCasing() = runTest {
        // Given
        val uuid = "E2fcD000-8ce3-11F1-9876-d923122AC2fc"
        val input = "\u0004doordeck.link/$uuid"

        // When
        val result = TileUrlParser.parseTileUrl(input, TileUrlSource.NFC)

        // Then
        assertEquals(uuid, result.tileId)
        assertEquals("https://doordeck.link/$uuid", result.url)
    }

    @Test
    fun differentlyCasedSameUuidProduceDistinctTileIdStrings() = runTest {
        // Given
        val lower = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val upper = "0C019AD0-38D4-11F1-8662-339EF0F86A15"

        // When
        val resultLower = TileUrlParser.parseTileUrl(
            "https://doordeck.link/$lower", TileUrlSource.OTHER
        )
        val resultUpper = TileUrlParser.parseTileUrl(
            "https://doordeck.link/$upper", TileUrlSource.OTHER
        )

        // Then
        assertNotEquals(resultLower.tileId, resultUpper.tileId)
        assertEquals(lower, resultLower.tileId)
        assertEquals(upper, resultUpper.tileId)
    }

    @Test
    fun uuidWithInvalidHexCharacterThrows() = runTest {
        // Given
        val invalidUuid = "0c019adg-38d4-11f1-8662-339ef0f86a1z"
        val input = "https://doordeck.link/$invalidUuid"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals(
            "Last path segment: $invalidUuid, is not a valid tile UUID",
            exception.message
        )
    }

    @Test
    fun uuidWithWrongSegmentLengthsThrows() = runTest {
        // Given
        val invalidUuid = "0c019a-d038d4-11f1866-2339ef0f86a15"
        val input = "https://doordeck.link/$invalidUuid"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals(
            "Last path segment: $invalidUuid, is not a valid tile UUID",
            exception.message
        )
    }

    @Test
    fun uuidWithMissingHyphensThrows() = runTest {
        // Given
        val noSeparatorsUuid = "0c019ad038d411f18662339ef0f86a15"
        val input = "https://doordeck.link/$noSeparatorsUuid"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals(
            "Last path segment: $noSeparatorsUuid, is not a valid tile UUID",
            exception.message
        )
    }

    @Test
    fun uuidWithExtraCharacterThrows() = runTest {
        // Given
        val invalidUuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15a"
        val input = "https://doordeck.link/$invalidUuid"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals(
            "Last path segment: $invalidUuid, is not a valid tile UUID",
            exception.message
        )
    }

    @Test
    fun malformedSchemeThrowsInvalidUrlFormat() = runTest {
        // Given
        val input = "ht tp://doordeck.link/0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("Invalid URL format: $input", exception.message)
    }

    @Test
    fun malformedPercentEncodingThrowsInvalidUrlFormat() = runTest {
        // Given
        val input = "https://doordeck.link/0c019ad0-38d4-11f1-8662-339ef0f86a15%3"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("Invalid URL format: $input", exception.message)
    }

    @Test
    fun arbitraryHostThrows() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val input = "https://totally-not-doordeck.evil.com/$uuid"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(input, TileUrlSource.OTHER)
        }

        // Then
        assertEquals("Invalid URL host: $input", exception.message)
    }
}