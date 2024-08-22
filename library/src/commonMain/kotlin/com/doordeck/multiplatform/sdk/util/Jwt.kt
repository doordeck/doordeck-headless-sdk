package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.JSON
import io.ktor.utils.io.core.String
import kotlinx.datetime.Instant
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.io.encoding.Base64

object Jwt {

    private const val USER_ID_FIELD = "sub"
    private const val USER_EMAIL_FIELD = "email"
    private const val TOKEN_EXPIRE_AT_FIELD = "exp"

    private fun getClaims(token: String): Map<String, String>? {
        val parts = token.split(".")
        if (parts.size != 3) {
            return null
        }
        val decodedPayload = String(Base64.decode(parts[1])) // TODO This is failing but is not used right now
        val json = JSON.parseToJsonElement(decodedPayload)
        return json.jsonObject.map { it.key to it.value.jsonPrimitive.content }.toMap()
    }

    fun String.getUserIdFromToken(): String? =
        getClaims(this)?.get(USER_ID_FIELD)

    fun String.getEmailFromToken(): String? =
        getClaims(this)?.get(USER_EMAIL_FIELD)

    fun String.getExpireAtFromToken(): Instant? =
        getClaims(this)?.get(TOKEN_EXPIRE_AT_FIELD)?.let { Instant.fromEpochSeconds(it.toLong()) }

    fun Map<String, String>?.getUserIdFromClaims(): String? =
        this?.get(USER_ID_FIELD)

    fun Map<String, String>?.getUserEmailFromClaims(): String? =
        this?.get(USER_EMAIL_FIELD)

    fun Map<String, String>?.getExpireAtFromClaims(): Instant? =
        this?.get(TOKEN_EXPIRE_AT_FIELD)?.let { Instant.fromEpochSeconds(it.toLong()) }
}