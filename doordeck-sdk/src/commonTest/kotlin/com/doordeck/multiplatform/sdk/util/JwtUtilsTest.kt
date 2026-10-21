package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.randomJwt
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenInvalidOrExpired
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenInvalidOrPastExpiration
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class JwtUtilsTest {

    @Test
    fun shouldVerifyTokenExpirationFuture() = runTest {
        // Given exp - 2050
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA"

        // When
        val result = token.isJwtTokenInvalidOrExpired()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldVerifyTokenExpirationExpired() = runTest {
        // Given exp - 2023
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIxNzAxNjk2NDYxIn0.L0aOoYwgqXBfGETZYB3zLW2t5L06oMrgUVpC6jXu1zfHzbusCniyD7jFybAhyQTAt4_ZirGVtwBzaukBUw0aTA"

        // When
        val result = token.isJwtTokenInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyTokenExpirationInvalid() = runTest {
        // Given
        val token = "IAMATOKEN"

        // When
        val result = token.isJwtTokenInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyTokenExpirationNoExp() = runTest {
        // Given
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.e30.u_rPJDKVGtpoPbpRnPL0Qju6lqBUYaskskptJuNIan0WwL91qMLXaSzGTnBPPDAdT9M3208oVyMlK3A4gKqc2w"

        // When
        val result = token.isJwtTokenInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyTokenPastExpirationFuture() = runTest {
        // Given exp - 2050
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA"

        // When
        val result = token.isJwtTokenInvalidOrPastExpiration()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldVerifyTokenPastExpirationExpired() = runTest {
        // Given exp - 2023
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIxNzAxNjk2NDYxIn0.L0aOoYwgqXBfGETZYB3zLW2t5L06oMrgUVpC6jXu1zfHzbusCniyD7jFybAhyQTAt4_ZirGVtwBzaukBUw0aTA"

        // When
        val result = token.isJwtTokenInvalidOrPastExpiration()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyTokenPastExpirationInvalid() = runTest {
        // Given
        val token = "IAMATOKEN"

        // When
        val result = token.isJwtTokenInvalidOrPastExpiration()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyTokenPastExpirationNoExp() = runTest {
        // Given
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.e30.u_rPJDKVGtpoPbpRnPL0Qju6lqBUYaskskptJuNIan0WwL91qMLXaSzGTnBPPDAdT9M3208oVyMlK3A4gKqc2w"

        // When
        val result = token.isJwtTokenInvalidOrPastExpiration()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyTokenExpiringWithinMinimumLifetime() = runTest {
        // Given
        val token = randomJwt(SystemClock.now().plus(1.hours))

        // When
        val invalidOrExpired = token.isJwtTokenInvalidOrExpired()
        val invalidOrPastExpiration = token.isJwtTokenInvalidOrPastExpiration()

        // Then
        assertTrue { invalidOrExpired }
        assertFalse { invalidOrPastExpiration }
    }

    @Test
    fun shouldVerifyTokenExpiringAfterMinimumLifetime() = runTest {
        // Given
        val token = randomJwt(SystemClock.now().plus(2.days))

        // When
        val invalidOrExpired = token.isJwtTokenInvalidOrExpired()
        val invalidOrPastExpiration = token.isJwtTokenInvalidOrPastExpiration()

        // Then
        assertFalse { invalidOrExpired }
        assertFalse { invalidOrPastExpiration }
    }

    @Test
    fun shouldVerifyTokenJustPastExpiration() = runTest {
        // Given
        val token = randomJwt(SystemClock.now().minus(1.minutes))

        // When
        val invalidOrExpired = token.isJwtTokenInvalidOrExpired()
        val invalidOrPastExpiration = token.isJwtTokenInvalidOrPastExpiration()

        // Then
        assertTrue { invalidOrExpired }
        assertTrue { invalidOrPastExpiration }
    }
}