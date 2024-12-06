package com.doordeck.multiplatform.sdk.util

import kotlinx.coroutines.test.runTest
import kotlin.test.Test

import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CertificateUtilsTest {
    @Test
    fun shouldVerifyExpireDateFuture() = runTest {
        // Given
        val certificate = "MIIDADCCAeigAwIBAgIGAZOS7Z/dMA0GCSqGSIb3DQEBCwUAMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTAeFw0yNDEyMDQxODI3NDZaFw00NDExMjkxODI3NDZaMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKVUdDhA08HC4GycerNe2PdHre6tZfviddT3L9vGalTVjBl+5VBfsKieg8tgfYANh1tfDEBzIfRkm2/AQ8hUK+0f8oAzo/utgM9r9LR1oadU6WTX8UiEpkzBVjpazh05dMDZhZyNRpfWLgGcqQ/rzMkHAeD3XG2FcYvy35HFnMMtPcSVhYWwKaeoBB0fiiGaYdkJz2YiZN35jPMCB83uBgxiaPkTfK47S9pZz89tztvMGq9oXewOS2rxIljECcGEB3kQlWRWOVvw5nZIKzv2TxPoXWZKBfRcL/nTgURaiCACD3m+rNYHzkWgdXUo4ICA9WB7wzFkPdjv0oyl65r9SQcCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAlVSckJ68fF1l70TvUrMUUMooD64q3dX3rEsoKcQg6vY9gwzKNUAjIh0wTllF1RmtNux9NUm0wEhWqT6f3EpQAnYTFxhmF3nyop7O2ybFDXmW6lCppVKTMQuacgPMZwEZbaPNEeznCZloGnoJw7WFdLm3sC0+KGagEn96kaYe/ZzxE8iA1Jj0OZu+4UggkmdQ0LqWHnWiSvqNzlC806kVrg47E6RmmTh/gMCykbEHTGX3cyAiT0VGjDkvMdvINEx2dwHDCFh9Szm3raC4a0g0S+EknATwUIrZhuIhOch0aXH+m/Oove286mr27tesqN6ZEw6j5hKO5Jj7KnS9Hon7BA=="

        // When
        val result = certificate.isCertificateAboutToExpire()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldVerifyExpireDateExpired() = runTest {
        // Given
        val certificate = "MIIDADCCAeigAwIBAgIGAZOS8A/NMA0GCSqGSIb3DQEBCwUAMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTAeFw0yNDEyMDQxODMwMjZaFw0yNDEyMDUxODMwMjZaMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMIdLEAhDBz8cpqN9I8KNpVrdHmcobNqmHxpN36KmJ7G5K5i7Uv3SPKfrLw2zvf15+VPkvxDIeciegRkEfW5p0rjJy0ecKudi7kSmQYxKs8m/PMYmT/0qHpx+I9i3Uz/Wln7mvFIvQi+855USMgh37N6kruBO1bBDCSpM/yS0WC3szdmTG4QHxFRqlDkzGuLDhT+thM1mzhRhwTuL/+B9C3Z4SP/wd+lWQXeOtwRIZgjUrLbczwdVUeIR5tG4bV46y2OSMvwTrFVa87ZkcBpOzkVXsK4oMpujYJsoMJyOcRPjdFfpZC2B6tcQhI5p31/SrWPE6Y8M1/ctgqkks/80ykCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAlID39SmMzMMwpUHpDLAspsIXXG6z9AxPSGWY5h8vgaMukUSPZ8rKHbTBU29UrIOw3vzcapn06dDlQtaWLiatjHM3RK6JxMEciggWTr5G3EkM0FV4IBVVJXFyED9HW9wW9MZjijVN5LC+5yV50qZifUt/LwCyS6QZJ/mry2WQ/LZeNmt5VjPKAnP8CrQHa/m4My0rbxglJkjm5Bsh4J+tOp7wDgUZQqyCD0iQ6tUBx+lfKW4wwJFO9EAXz73oH+DHXDT1gTwWaqVVonJBeRsgXfGf7bWe1h8iNBCnXYOMf4qJcwJYADYab1GnpAlscn++KSXJtwWoWXSOTet6DRtf9A=="

        // When
        val result = certificate.isCertificateAboutToExpire()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyExpireDateInvalid() = runTest {
        // Given
        val certificate = "IAMACERTIFICATE"

        // When
        val result = certificate.isCertificateAboutToExpire()

        // Then
        assertTrue { result }
    }
}