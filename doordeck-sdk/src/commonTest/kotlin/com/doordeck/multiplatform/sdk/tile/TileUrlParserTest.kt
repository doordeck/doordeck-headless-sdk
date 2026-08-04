package com.doordeck.multiplatform.sdk.tile

import com.doordeck.multiplatform.sdk.exceptions.InvalidTileUrlException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TileUrlParserTest {

    @Test
    fun dnaQueryStringIsPreservedAndNotExtracted() = runTest {
        // Given
        val uuid = "e2fcd000-8ce3-11f1-9876-d923122ac2fc"
        val input = "https://doordeck.link/$uuid?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07"

        // When
        val result = TileUrlParser.parseTileUrl(
            input = input,
            source = TileUrlSource.OTHER
        )

        // Then
        assertEquals(uuid, result.tileId)
        assertEquals(input, result.url)
    }

    @Test
    fun simpleTileUrl() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val result = TileUrlParser.parseTileUrl(
            input = "https://doordeck.link/$uuid",
            source = TileUrlSource.OTHER
        )

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun trailingSlashIsIgnored() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val result = TileUrlParser.parseTileUrl(
            input = "https://doordeck.link/$uuid/",
            source = TileUrlSource.OTHER
        )

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun extraPathPrefixSegmentsAreIgnored() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val result = TileUrlParser.parseTileUrl(
            input = "https://doordeck.link/uuid/$uuid",
            source = TileUrlSource.OTHER
        )

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun deepNestedPathWithTrailingSlashAndQueryStillFindsLastSegment() = runTest {
        // Given
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val result = TileUrlParser.parseTileUrl(
            input = "https://this.thirdparty.com/tile/scan/what/not/$uuid/?uuid=hello",
            source = TileUrlSource.OTHER
        )

        // Then
        assertEquals(uuid, result.tileId)
    }

    @Test
    fun nfcPayloadWithHttpsPrefixCodeIsDecompressed() = runTest {
        // Given
        val uuid = "e2fcd000-8ce3-11f1-9876-d923122ac2fc"
        val payload = "\u0004doordeck.link/$uuid?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07"

        // When
        val result = TileUrlParser.parseTileUrl(
            input = payload,
            source = TileUrlSource.NFC
        )

        // Then
        assertEquals(uuid, result.tileId)
        assertEquals(
            "https://doordeck.link/$uuid?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07",
            result.url
        )
    }

    @Test
    fun nfcPayloadWithNoAbbreviationCodeIsPassed() = runTest {
        val uuid = "0c019ad0-38d4-11f1-8662-339ef0f86a15"
        val url = "https://doordeck.link/$uuid"
        val payload = "\u0000$url"

        // When
        val result = TileUrlParser.parseTileUrl(
            input = payload,
            source = TileUrlSource.NFC
        )

        // Then
        assertEquals(uuid, result.tileId)
        assertEquals(url, result.url)
    }

    @Test
    fun bareUuidWithNoUrlWrapperParses() = runTest {
        // Given
        val input = "0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val result = TileUrlParser.parseTileUrl(
            input = input,
            source = TileUrlSource.OTHER
        )

        // Then
        assertEquals(input, result.tileId)
    }

    @Test
    fun nonUuidLastSegmentThrows() = runTest {
        // Given
        val input = "https://doordeck.link/not-a-uuid"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(
                input = input,
                source = TileUrlSource.OTHER
            )
        }

        // Then
        assertEquals("Last path segment 'not-a-uuid' is not a valid tile UUID", exception.message)
    }

    @Test
    fun noPathSegmentsThrows() = runTest {
        // Given
        val input = "https://doordeck.link/"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(
                input = input,
                source = TileUrlSource.OTHER
            )
        }

        // Then
        assertEquals("No path segments found in '$input'", exception.message)
    }

    @Test
    fun extraTrailingSegmentAfterUuidThrows() = runTest {
        // Given
        val input = "https://doordeck.link/0c019ad0-38d4-11f1-8662-339ef0f86a15/extra-segment"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(
                input = input,
                source = TileUrlSource.OTHER
            )
        }

        // Then
        assertEquals("Last path segment 'extra-segment' is not a valid tile UUID", exception.message)
    }

    @Test
    fun uuidInQueryStringIsNeverUsedAsTileId() = runTest {
        // Given
        val input = "https://doordeck.link/?uuid=0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(
                input = input,
                source = TileUrlSource.OTHER
            )
        }

        // Then
        assertEquals("No path segments found in '$input'", exception.message)
    }

    @Test
    fun emptyInputShouldThrow() = runTest {
        // Given
        val input = ""

        // When
        val exception = assertFailsWith<InvalidTileUrlException> {
            TileUrlParser.parseTileUrl(
                input = input,
                source = TileUrlSource.OTHER
            )
        }

        // Then
        assertEquals("Tile url is empty", exception.message)
    }
}