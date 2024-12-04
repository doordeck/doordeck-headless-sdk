package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenAboutToExpire
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class JwtUtilsTest {

    @Test
    fun shouldVerifyTokenExpirationFuture() = runTest {
        // Given exp - 2050
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA"

        // When
        val result = token.isJwtTokenAboutToExpire()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldVerifyTokenExpirationExpired() = runTest {
        // Given exp - 2023
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIxNzAxNjk2NDYxIn0.L0aOoYwgqXBfGETZYB3zLW2t5L06oMrgUVpC6jXu1zfHzbusCniyD7jFybAhyQTAt4_ZirGVtwBzaukBUw0aTA"

        // When
        val result = token.isJwtTokenAboutToExpire()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyTokenExpirationInvalid() = runTest {
        // Given
        val token = "IAMATOKEN"

        // When
        val result = token.isJwtTokenAboutToExpire()

        // Then
        assertTrue { result }
    }
}