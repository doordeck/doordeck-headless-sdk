package com.doordeck.sdk.util

import com.doordeck.sdk.JSON
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.io.encoding.Base64

object Jwt {

    private fun getClaims(token: String): Map<String, String>? {
        val parts = token.split(".")
        if (parts.size != 3) {
            return null
        }
        val decodedPayload = String(Base64.decode(parts[1]))
        val json = JSON.parseToJsonElement(decodedPayload)
        return json.jsonObject.map { it.key to it.value.jsonPrimitive.content }.toMap()
    }

    fun getSub(token: String): String? =
        getClaims(token)?.get("sub")
}