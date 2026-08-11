package com.doordeck.multiplatform.sdk.tile

import com.doordeck.multiplatform.sdk.exceptions.InvalidTileUrlException
import io.ktor.http.URLProtocol
import io.ktor.http.parseUrl
import kotlin.js.JsExport
import kotlin.uuid.Uuid

@JsExport
object TileUrlParser {

    // NFC Forum "URI Record Type Definition" (NDEF), URI Identifier Code table.
    // Index = first payload byte value.
    private val URI_PREFIXES = arrayOf(
        "",                           // 0x00 - no abbreviation, URI stored in full
        "http://www.",                // 0x01
        "https://www.",               // 0x02
        "http://",                    // 0x03
        "https://"                    // 0x04
    )

    /**
     * @throws InvalidTileUrlException if no tile ID can be extracted
     */
    fun parseTileUrl(input: String, source: String): ParsedTileUrl {
        return parseTileUrl(input, TileUrlSource.valueOf(source))
    }

    /**
     * @throws InvalidTileUrlException if no tile ID can be extracted
     */
    @JsExport.Ignore
    @Throws(Exception::class)
    fun parseTileUrl(input: String, source: TileUrlSource): ParsedTileUrl {
        if (input.isEmpty()) {
            throw InvalidTileUrlException("Input is empty")
        }

        val parsedUrlString = when (source) {
            TileUrlSource.NFC -> decompressNfcPayload(input)
            TileUrlSource.OTHER -> input
        }

        val url = parseUrl(parsedUrlString)
            ?: throw InvalidTileUrlException("Invalid URL format: $parsedUrlString")

        if ((url.protocolOrNull != URLProtocol.HTTP && url.protocolOrNull != URLProtocol.HTTPS) ||
            url.user != null || url.password != null) {
            throw InvalidTileUrlException("Invalid URL format: $url")
        }

        val segments = url.segments.filter { it.isNotEmpty() }

        val lastSegment = segments.lastOrNull { it.isNotEmpty() }
            ?: throw InvalidTileUrlException("No path segments found in: $url")

        Uuid.parseHexDashOrNull(lastSegment)
            ?: throw InvalidTileUrlException("Last path segment: $lastSegment, is not a valid tile UUID")

        return ParsedTileUrl(tileId = lastSegment, url = parsedUrlString)
    }

    /**
     * Decompresses a raw NDEF URI record payload back into a full URL string.
     *
     * The first byte of the payload is always the URI Identifier Code (never payload data) and is
     * always consumed, regardless of its value. recognized codes (0x00-0x04) are expanded via
     * [URI_PREFIXES]; if the code is not recognized, the payload is returned unchanged.
     *
     * @return the decompressed URL string, or the original payload if the identifier code is unrecognized.
     */
    private fun decompressNfcPayload(payload: String): String {
        val code = payload[0].code
        val prefix = URI_PREFIXES.getOrNull(code)
        return if (prefix != null) prefix + payload.substring(1) else payload
    }
}