package com.doordeck.multiplatform.sdk.tile

import com.doordeck.multiplatform.sdk.exceptions.InvalidTileUrlException
import io.ktor.http.URLParserException
import io.ktor.http.Url
import kotlin.js.JsExport
import kotlin.uuid.Uuid

@JsExport
object TileUrlParser {

    // NFC Forum "URI Record Type Definition" (NDEF), URI Identifier Code table.
    // Index = first payload byte value. Codes 0x24-0xFF are reserved/unassigned by the spec.
    // No compliant NDEF writer emits a reserved code, so encountering one here almost always means
    // the input isn't actually an NDEF URI payload (e.g. wrong TileUrlSource passed) or the bytes
    // were corrupted/mis-decoded upstream. Reserved codes are therefore rejected rather than
    // silently falling back to "no prefix" — the first byte is always positional (the identifier
    // code) and stripping it without a matching prefix would reconstruct a truncated, wrong URL.
    // That's especially dangerous here since the URL's query string (uid/ctr/cmac for NTAG 424 DNA)
    // feeds server-side CMAC verification, so a subtly-mangled but still-parseable URL is a worse
    // failure mode than a loud, immediate throw. See decompressNfcPayload below.
    private val URI_PREFIXES = arrayOf(
        "",                           // 0x00 - no abbreviation, URI stored in full
        "http://www.",                // 0x01
        "https://www.",               // 0x02
        "http://",                    // 0x03
        "https://",                   // 0x04
        "tel:",                       // 0x05
        "mailto:",                    // 0x06
        "ftp://anonymous:anonymous@", // 0x07
        "ftp://ftp.",                 // 0x08
        "ftps://",                    // 0x09
        "sftp://",                    // 0x0A
        "smb://",                     // 0x0B
        "nfs://",                     // 0x0C
        "ftp://",                     // 0x0D
        "dav://",                     // 0x0E
        "news:",                      // 0x0F
        "telnet://",                  // 0x10
        "imap:",                      // 0x11
        "rtsp://",                    // 0x12
        "urn:",                       // 0x13
        "pop:",                       // 0x14
        "sip:",                       // 0x15
        "sips:",                      // 0x16
        "tftp:",                      // 0x17
        "btspp://",                   // 0x18
        "btl2cap://",                 // 0x19
        "btgoep://",                  // 0x1A
        "tcpobex://",                 // 0x1B
        "irdaobex://",                // 0x1C
        "file://",                    // 0x1D
        "urn:epc:id:",                // 0x1E
        "urn:epc:tag:",               // 0x1F
        "urn:epc:pat:",               // 0x20
        "urn:epc:raw:",               // 0x21
        "urn:epc:",                   // 0x22
        "urn:nfc:"                    // 0x23
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
    fun parseTileUrl(input: String, source: TileUrlSource): ParsedTileUrl {
        if (input.isEmpty()) {
            throw InvalidTileUrlException("Tile url is empty")
        }

        val url = when (source) {
            TileUrlSource.NFC -> decompressNfcPayload(input)
            TileUrlSource.OTHER -> input
        }

        val segments = try {
            Url(url).segments.filter { it.isNotEmpty() }
        } catch (_: URLParserException) {
            throw InvalidTileUrlException("Invalid URL format: $url")
        }

        val lastSegment = segments.lastOrNull()
            ?: throw InvalidTileUrlException("No path segments found in: $url")

        Uuid.parseHexDashOrNull(lastSegment)
            ?: throw InvalidTileUrlException("Last path segment: $lastSegment, is not a valid tile UUID")

        return ParsedTileUrl(tileId = lastSegment, url = url)
    }

    /**
     * Decompresses a raw NDEF URI record payload back into a full URL string.
     *
     * The first byte of the payload is always the URI Identifier Code (never payload data) and is
     * always consumed, regardless of its value. Recognised codes (0x00-0x23) are expanded via
     * [URI_PREFIXES]; reserved codes (0x24-0xFF) are rejected with [InvalidTileUrlException] rather
     * than treated as "no prefix", since silently proceeding would reconstruct a URL missing its
     * first character with no way to detect the corruption downstream.
     *
     * @throws InvalidTileUrlException if the identifier code is reserved/unrecognised
     */
    private fun decompressNfcPayload(payload: String): String {
        val code = payload[0].code
        val prefix = URI_PREFIXES.getOrNull(code)
            ?: throw InvalidTileUrlException("Invalid identifier code: $code")
        return prefix + payload.substring(1)
    }
}