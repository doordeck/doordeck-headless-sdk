package com.doordeck.multiplatform.sdk.tile

import com.doordeck.multiplatform.sdk.exceptions.InvalidTileUrlException
import io.ktor.http.URLProtocol
import io.ktor.http.parseUrl
import kotlin.js.JsExport
import kotlin.native.HiddenFromObjC
import kotlin.uuid.Uuid

/**
 * Extracts a tile ID from a URL.
 *
 * Accepts either a plain URL or a raw NDEF URI record payload (see [TileUrlSource]).
 * A URL is considered valid only if it is http/https, carries no userinfo, and its last
 * non-empty path segment is a hex-dash UUID — that segment is the tile ID.
 *
 * Note: validation is deliberately permissive — it checks only the scheme, the absence of
 * userinfo, and that the last path segment is a UUID. Everything else (host, port, query,
 * fragment, encoding) is accepted as parsed. A [ParsedTileUrl.url] should therefore be treated
 * as "well-formed enough to yield a tile ID", not as a fully validated or reachable URL;
 * re-validate before using it for anything other than the tile ID.
 */
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
     * JS-friendly overload taking the source as a string.
     *
     * @param input a URL, or an NDEF URI record payload when [source] is `NFC`.
     * @param source the name of a [TileUrlSource] constant, e.g. `"NFC"` or `"OTHER"`.
     * @throws IllegalArgumentException if [source] is not a valid [TileUrlSource] name.
     * @throws InvalidTileUrlException if no tile ID can be extracted.
     */
    @HiddenFromObjC
    fun parseTileUrl(input: String, source: String): ParsedTileUrl {
        return parseTileUrl(input, TileUrlSource.valueOf(source))
    }

    /**
     * Parses [input] and extracts the tile ID from its last path segment.
     *
     * When [source] is [TileUrlSource.NFC], [input] is first decompressed from an NDEF URI
     * record payload; otherwise it is used as-is.
     *
     * Validation is minimal, so the returned [ParsedTileUrl.url] may still be malformed in ways
     * this parser does not inspect — only the tile ID is guaranteed to be a valid UUID.
     *
     * @param input a non-empty URL, or an NDEF URI record payload when [source] is NFC.
     * @param source how [input] is encoded.
     * @return the tile ID plus the decompressed URL it was taken from.
     * @throws InvalidTileUrlException if [input] is empty, is not a parsable URL, is not
     * http/https, contains userinfo, has no path segments, or its last segment is not a UUID.
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
     * Decompresses a raw NDEF URI record payload into a full URL string.
     *
     * The first byte is the URI Identifier Code. If it maps to a known prefix (0x00–0x04 in
     * [URI_PREFIXES]) that byte is dropped and the prefix prepended to the remainder; otherwise
     * the payload is returned verbatim, identifier byte included.
     *
     * @param payload a non-empty NDEF URI record payload.
     * @return the expanded URL, or [payload] unchanged if the identifier code is unrecognized.
     */
    private fun decompressNfcPayload(payload: String): String {
        val code = payload[0].code
        val prefix = URI_PREFIXES.getOrNull(code)
        return if (prefix != null) prefix + payload.substring(1) else payload
    }
}