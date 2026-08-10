package com.doordeck.multiplatform.sdk.tile

import com.doordeck.multiplatform.sdk.exceptions.InvalidTileUrlException
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.tile.TileUrlSource.NFC
import com.doordeck.multiplatform.sdk.tile.TileUrlSource.OTHER
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class TileUrlParserTest {

    private val testUuid = randomUuidString()

    private val successCases = listOf(
        // OTHER
        // DNA query is preserved
        TestCase("https://doordeck.link/e2fcd000-8ce3-11f1-9876-d923122ac2fc?uid=047448CA9C1790&ctr=000011&cmac=C780B85F5F3DAD07",
            OTHER, SuccessResult("e2fcd000-8ce3-11f1-9876-d923122ac2fc")),
        // Ending trailing slash
        TestCase("https://doordeck.link/$testUuid/", OTHER, SuccessResult()),
        // Extra path segments
        TestCase("https://doordeck.link/uuid/$testUuid", OTHER, SuccessResult()),
        // Deep nested path with trailing slash and query
        TestCase("https://this.thirdparty.com/tile/scan/what/not/$testUuid/?uuid=hello", OTHER, SuccessResult()),
        // Mixed case UUID
        TestCase("https://doordeck.link/E2fcD000-8ce3-11F1-9876-d923122AC2fc", OTHER, SuccessResult("E2fcD000-8ce3-11F1-9876-d923122AC2fc")),
        // Fully upper case UUID
        TestCase("https://doordeck.link/E2FCD000-8CE3-11F1-9876-D923122AC2FC", OTHER, SuccessResult("E2FCD000-8CE3-11F1-9876-D923122AC2FC")),
        // Non https scheme
        TestCase("http://doordeck.link/$testUuid", OTHER, SuccessResult()),
        // Custom scheme
        TestCase("doordeck://open/$testUuid", OTHER, SuccessResult()),

        // NCF
        // NFC with empty prefix code & http www
        TestCase("\u0000http://www.doordeck.link/$testUuid", NFC, SuccessResult(url = "http://www.doordeck.link/$testUuid")),
        // NFC with empty prefix code & https www
        TestCase("\u0000https://www.doordeck.link/$testUuid", NFC, SuccessResult(url = "https://www.doordeck.link/$testUuid")),
        // NFC with empty prefix code & http
        TestCase("\u0000http://doordeck.link/$testUuid", NFC, SuccessResult(url = "http://doordeck.link/$testUuid")),
        // NFC with empty prefix code & https
        TestCase("\u0000https://doordeck.link/$testUuid", NFC, SuccessResult(url = "https://doordeck.link/$testUuid")),
        // NFC with http www prefix code
        TestCase("\u0001doordeck.link/$testUuid", NFC, SuccessResult(url = "http://www.doordeck.link/$testUuid")),
        // NFC with https www prefix code
        TestCase("\u0002doordeck.link/$testUuid", NFC, SuccessResult(url = "https://www.doordeck.link/$testUuid")),
        // NFC with http prefix code
        TestCase("\u0003doordeck.link/$testUuid", NFC, SuccessResult(url = "http://doordeck.link/$testUuid")),
        // NFC with https prefix code
        TestCase("\u0004doordeck.link/$testUuid", NFC, SuccessResult(url = "https://doordeck.link/$testUuid")),
        // NFC with full URL
        TestCase("https://doordeck.link/$testUuid", NFC, SuccessResult()),
        // NFC with mixed case UUID
        TestCase("\u0004doordeck.link/E2fcD000-8ce3-11F1-9876-d923122AC2fc",
            NFC, SuccessResult("E2fcD000-8ce3-11F1-9876-d923122AC2fc", "https://doordeck.link/E2fcD000-8ce3-11F1-9876-d923122AC2fc"))
    )

    private val failureCases = listOf(
        // Empty URL
        TestCase("", OTHER, FailureResult("Input is empty")),
        // Malformed scheme
        TestCase("ht tp://doordeck.link/$testUuid", OTHER, FailureResult("Invalid URL format: ht tp://doordeck.link/$testUuid")),
        // Malformed encoding
        TestCase("https://doordeck.link/$testUuid%3", OTHER, FailureResult("Invalid URL format: https://doordeck.link/$testUuid%3")),
        // URL with user info
        TestCase("https://user:pass@doordeck.link/$testUuid", OTHER, FailureResult("Invalid URL format: https://user:pass@doordeck.link/$testUuid")),
        // Base UUID
        TestCase(testUuid, OTHER, FailureResult("Invalid URL format: $testUuid")),
        // UUID with invalid HEX characters
        TestCase("https://doordeck.link/0c019adg-38d4-11f1-8662-339ef0f86a1z", OTHER, FailureResult("Last path segment: 0c019adg-38d4-11f1-8662-339ef0f86a1z, is not a valid tile UUID")),
        // UUID with invalid segment length
        TestCase("https://doordeck.link/0c019a-d038d4-11f1866-2339ef0f86a15", OTHER, FailureResult("Last path segment: 0c019a-d038d4-11f1866-2339ef0f86a15, is not a valid tile UUID")),
        // UUID with missing hyphens
        TestCase("https://doordeck.link/0c019ad038d411f18662339ef0f86a15", OTHER, FailureResult("Last path segment: 0c019ad038d411f18662339ef0f86a15, is not a valid tile UUID")),
        // UUID with extra character
        TestCase("https://doordeck.link/0c019ad0-38d4-11f1-8662-339ef0f86a15a", OTHER, FailureResult("Last path segment: 0c019ad0-38d4-11f1-8662-339ef0f86a15a, is not a valid tile UUID")),
        // Non UUID last segment
        TestCase("https://doordeck.link/not-a-uuid", OTHER, FailureResult("Last path segment: not-a-uuid, is not a valid tile UUID")),
        // No path segments
        TestCase("https://doordeck.link/", OTHER, FailureResult("No path segments found in: https://doordeck.link/")),
        // UUID only in query string
        TestCase("https://doordeck.link/?uuid=$testUuid", OTHER, FailureResult("No path segments found in: https://doordeck.link/?uuid=$testUuid")),
        // Extra trailing segments after UUID
        TestCase("https://doordeck.link/0c019ad0-38d4-11f1-8662-339ef0f86a15/extra-segment", OTHER, FailureResult("Last path segment: extra-segment, is not a valid tile UUID")),
        // NFC with unknown prefix code
        TestCase("\u0006doordeck.link/$testUuid", NFC, FailureResult("Invalid URL format: \u0006doordeck.link/$testUuid")),
        // NFC without prefix
        TestCase("doordeck.link/$testUuid", NFC, FailureResult("Invalid URL format: doordeck.link/$testUuid"))
    )

    @Test
    fun shouldSuccessToParseTileUr() = runTest {
        // Given
        successCases.forEach { case ->
            // When
            val result = TileUrlParser.parseTileUrl(case.url, case.type)

            // Then
            assertIs<SuccessResult>(case.result)
            assertEquals(case.result.uuid ?: testUuid, result.tileId)
            assertEquals(case.result.url ?: case.url, result.url)
        }
    }

    @Test
    fun shouldFailToParseTileUrl() = runTest {
        // Given
        failureCases.forEach { case ->
            // When
            val exception = assertFailsWith<InvalidTileUrlException> {
                TileUrlParser.parseTileUrl(case.url, case.type)
            }

            // Then
            assertIs<FailureResult>(case.result)
            assertEquals(case.result.message, exception.message)
        }
    }

    @Test
    fun stringSourceOverloadWithUnknownSourceThrowsIllegalArgument() = runTest {
        // Given
        val input = "https://doordeck.link/0c019ad0-38d4-11f1-8662-339ef0f86a15"

        // When / Then
        assertFailsWith<IllegalArgumentException> {
            TileUrlParser.parseTileUrl(input, "HELLO")
        }
    }

    private data class TestCase(
        val url: String,
        val type: TileUrlSource,
        val result: TestResult
    )

    private interface TestResult

    private data class SuccessResult(
        val uuid: String? = null, // if null TEST_UUID is used in the verification
        val url: String? = null // if null TestCase.url is used in the verification
    ) : TestResult

    private data class FailureResult(
        val message: String
    ) : TestResult
}