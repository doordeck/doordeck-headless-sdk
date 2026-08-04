package com.doordeck.multiplatform.sdk.tile

import com.doordeck.multiplatform.sdk.exceptions.InvalidTileUrlException
import io.ktor.http.URLParserException
import io.ktor.http.Url
import kotlin.js.JsExport
import kotlin.uuid.Uuid

@JsExport
object TileUrlParser {

    // NFC Forum "URI Record Type Definition" (NDEF), URI Identifier Code table.
    // Index = first payload byte value. Codes 0x24-0xFF are reserved; per the RTD spec they are
    // treated as "no prefix expansion" — the byte is still consumed as the identifier byte, it
    // just doesn't prepend anything (handled below by the array bounds check).
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
        if (input.isEmpty()) throw InvalidTileUrlException("Tile url is empty")

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
            ?: throw InvalidTileUrlException("No path segments found in '$url'")

        Uuid.parseHexDashOrNull(lastSegment)
            ?: throw InvalidTileUrlException("Last path segment '$lastSegment' is not a valid tile UUID")

        return ParsedTileUrl(tileId = lastSegment, url = url)
    }

    private fun decompressNfcPayload(payload: String): String {
        val code = payload[0].code
        val prefix = URI_PREFIXES.getOrNull(code) ?: ""
        return prefix + payload.substring(1)
    }
}