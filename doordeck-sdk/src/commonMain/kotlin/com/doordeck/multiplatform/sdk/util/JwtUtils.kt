package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.JSON
import io.ktor.util.decodeBase64String
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.js.JsExport
import kotlin.time.Duration.Companion.days

@JsExport
internal object JwtUtils {
    private const val TOKEN_EXPIRE_AT_FIELD = "exp"

    private fun getClaims(token: String): Map<String, String> {
        val parts = token.split(".")
        if (parts.size != 3) {
            return emptyMap()
        }
        val decodedPayload = parts[1].decodeBase64String()
        val json = JSON.parseToJsonElement(decodedPayload)
        return json.jsonObject.map { it.key to it.value.jsonPrimitive.content }.toMap()
    }

    fun String.isJwtTokenAboutToExpire(): Boolean {
        return getClaims(this)[TOKEN_EXPIRE_AT_FIELD]?.let {
            Clock.System.now() >= Instant.fromEpochSeconds(it.toLong()) - 1.days
        } ?: true
    }
}