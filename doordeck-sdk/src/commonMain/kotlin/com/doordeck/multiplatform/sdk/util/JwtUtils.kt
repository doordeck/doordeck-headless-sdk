package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.JSON
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import io.ktor.util.decodeBase64String
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.jvm.JvmSynthetic
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

@get:JvmSynthetic
internal val MIN_TOKEN_LIFETIME_DAYS = 1.days

internal object JwtUtils {
    private const val TOKEN_EXPIRE_AT_FIELD = "exp"

    private fun getClaims(token: String): Map<String, String> {
        return try {
            return JSON.parseToJsonElement(token.split(".")[1].decodeBase64String())
                .jsonObject
                .map { it.key to it.value.jsonPrimitive.content }
                .toMap()
        } catch (exception: Exception) {
            SdkLogger.e(exception) { "Failed to parse JWT token" }
            emptyMap()
        }
    }

    @JvmSynthetic
    internal fun String.isJwtTokenInvalidOrExpired(): Boolean {
        val expiration = getClaims(this)[TOKEN_EXPIRE_AT_FIELD]?.let {
            Instant.fromEpochSeconds(it.toLong())
        }
        if (expiration == null) {
            SdkLogger.d { "Unable to retrieve the expiration claim from the JWT" }
            return true
        }
        SdkLogger.d { "JWT expiration date is $expiration" }
        return Clock.System.now() >= expiration - MIN_TOKEN_LIFETIME_DAYS
    }
}