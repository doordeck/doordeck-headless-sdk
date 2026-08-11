package com.doordeck.multiplatform.sdk.tile

import kotlin.js.JsExport

/**
 * The result of successfully parsing a tile URL or NFC URI record payload.
 *
 * @property tileId the tile UUID exactly as it appeared in the source's last non-empty path
 *   segment (or the raw input itself, for a bare-UUID input with no URL wrapper). Casing is
 *   preserved as-is and is **not** normalized — validity checking against the UUID format is
 *   case-insensitive, but this value is not coerced to any particular case. Callers that need
 *   to compare or key on this value (e.g. cache lookups, equality checks against a
 *   server-returned ID) should case-fold it themselves if required.
 *
 * @property url the full URL the tile ID was extracted from, always as a plain [String].
 *   - For [TileUrlSource.OTHER], this is the input string, untouched.
 *   - For [TileUrlSource.NFC], this is the input payload after decompression — i.e. with the
 *     leading URI Identifier Code byte expanded back into its full prefix (see
 *     [TileUrlParser.decompressNfcPayload]).
 *   In both cases the query string and fragment are preserved exactly as received, even though
 *   they are never consulted when extracting [tileId]. This matters in particular for NTAG 424
 *   DNA (SUN/SDM) tags, whose `uid`/`ctr`/`cmac` query parameters must reach the backend intact
 *   for CMAC verification.
 */
@JsExport
data class ParsedTileUrl(
    val tileId: String,
    val url: String
)