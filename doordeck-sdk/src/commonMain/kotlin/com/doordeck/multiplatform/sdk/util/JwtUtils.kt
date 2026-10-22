package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.JSON
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

/**
 * Tokens expiring within this period are refreshed ahead of time, while the backend still accepts them.
 */
@get:JvmSynthetic
internal val MIN_TOKEN_LIFETIME_DAYS = 1.days

internal object JwtUtils {
    private const val TOKEN_EXPIRE_AT_FIELD = "exp"

    private fun getClaims(token: String): Map<String, String> {
        return try {
            return JSON.parseToJsonElement(token.split(".")[1].decodeBase64ToByteArray().decodeToString())
                .jsonObject
                .map { it.key to it.value.jsonPrimitive.content }
                .toMap()
        } catch (exception: Exception) {
            SdkLogger.e(exception) { "Failed to parse JWT token" }
            emptyMap()
        }
    }

    /**
     * Checks whether the token should be refreshed: it is invalid (e.g., malformed, without expiration claim) or
     * expires within [MIN_TOKEN_LIFETIME_DAYS]. Such a token may still be accepted by the backend, see
     * [isJwtTokenInvalidOrPastExpiration].
     */
    @JvmSynthetic
    internal fun String.isJwtTokenInvalidOrExpired(): Boolean = isJwtTokenInvalidOrExpiringWithin(MIN_TOKEN_LIFETIME_DAYS)

    /**
     * Checks whether the token is no longer usable: it is invalid (e.g., malformed, without expiration claim) or
     * past its expiration, so the backend rejects it.
     */
    @JvmSynthetic
    internal fun String.isJwtTokenInvalidOrPastExpiration(): Boolean = isJwtTokenInvalidOrExpiringWithin(Duration.ZERO)

    private fun String.isJwtTokenInvalidOrExpiringWithin(minLifetime: Duration): Boolean {
        val expiration = getClaims(this)[TOKEN_EXPIRE_AT_FIELD]?.let {
            Instant.fromEpochSeconds(it.toLong())
        }
        if (expiration == null) {
            SdkLogger.w { "Unable to retrieve the expiration claim from the JWT" }
            return true
        }
        SdkLogger.d { "JWT expiration date is $expiration" }
        return SystemClock.now() >= expiration - minLifetime
    }
}