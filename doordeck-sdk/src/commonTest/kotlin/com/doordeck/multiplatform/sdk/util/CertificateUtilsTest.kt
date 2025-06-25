package com.doordeck.multiplatform.sdk.util

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CertificateUtilsTest {
    @Test
    fun shouldVerifyExpireDateExpired() = runTest {
        // Given
        // 2024-12-05T18:30:26Z
        val certificate = "MIIDADCCAeigAwIBAgIGAZOS8A/NMA0GCSqGSIb3DQEBCwUAMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTAeFw0yNDEyMDQxODMwMjZaFw0yNDEyMDUxODMwMjZaMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMIdLEAhDBz8cpqN9I8KNpVrdHmcobNqmHxpN36KmJ7G5K5i7Uv3SPKfrLw2zvf15+VPkvxDIeciegRkEfW5p0rjJy0ecKudi7kSmQYxKs8m/PMYmT/0qHpx+I9i3Uz/Wln7mvFIvQi+855USMgh37N6kruBO1bBDCSpM/yS0WC3szdmTG4QHxFRqlDkzGuLDhT+thM1mzhRhwTuL/+B9C3Z4SP/wd+lWQXeOtwRIZgjUrLbczwdVUeIR5tG4bV46y2OSMvwTrFVa87ZkcBpOzkVXsK4oMpujYJsoMJyOcRPjdFfpZC2B6tcQhI5p31/SrWPE6Y8M1/ctgqkks/80ykCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAlID39SmMzMMwpUHpDLAspsIXXG6z9AxPSGWY5h8vgaMukUSPZ8rKHbTBU29UrIOw3vzcapn06dDlQtaWLiatjHM3RK6JxMEciggWTr5G3EkM0FV4IBVVJXFyED9HW9wW9MZjijVN5LC+5yV50qZifUt/LwCyS6QZJ/mry2WQ/LZeNmt5VjPKAnP8CrQHa/m4My0rbxglJkjm5Bsh4J+tOp7wDgUZQqyCD0iQ6tUBx+lfKW4wwJFO9EAXz73oH+DHXDT1gTwWaqVVonJBeRsgXfGf7bWe1h8iNBCnXYOMf4qJcwJYADYab1GnpAlscn++KSXJtwWoWXSOTet6DRtf9A=="

        // When
        val result = certificate.isCertificateInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyExpireDateInvalid() = runTest {
        // Given
        val certificate = "IAMACERTIFICATE"

        // When
        val result = certificate.isCertificateInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyGMTExpireDateFuture() = runTest {
        // Given
        // 2065-05-03T22:31:02Z
        val certificate = "MIICXjCCAcegAwIBAgIBADANBgkqhkiG9w0BAQ0FADBLMQswCQYDVQQGEwJlczESMBAGA1UECAwJQ2F0YWxvbmlhMREwDwYDVQQKDAhEb29yZGVjazEVMBMGA1UEAwwMZG9vcmRlY2suY29tMCAXDTI1MDUxMzIyMzEwMloYDzIwNjUwNTAzMjIzMTAyWjBLMQswCQYDVQQGEwJlczESMBAGA1UECAwJQ2F0YWxvbmlhMREwDwYDVQQKDAhEb29yZGVjazEVMBMGA1UEAwwMZG9vcmRlY2suY29tMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcpf7qHCJMGawKXbohAQLwA8sdcWrEwvXrvHCbKVNDjl4tJTykgU7xSaxyTV32Wep3p6k6tCg3Mcj2lSueXvPAJ1lVa+OB5vVwH5aCsckl0Z5QxYH58jhw/kRJqT/UviqMKKT7D2rx9Moegs+R1cHW7HQTteYrlzjQGmJbSTNDGwIDAQABo1AwTjAdBgNVHQ4EFgQUxmzN4x0YaxHexnO1ymRELYjzFx8wHwYDVR0jBBgwFoAUxmzN4x0YaxHexnO1ymRELYjzFx8wDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQ0FAAOBgQB4mpU3qPUTMw0OVUp76fpx1Nkjfe66O/ywDEojO4LHj55NvO3jZ5aPlMTFw/lTraWO/8qYfA1mEJXaT8TFTUnoGCuZVwzvs6rdh1oHrV2WocLqND2nqD5tY2iBkZrZUnW52/TqafD65SiHVUXUfW/12iBlSAQwghA0jhEwCesMfw=="

        // When
        val result = certificate.isCertificateInvalidOrExpired()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldVerifyUTCExpireDateFuture() = runTest {
        // Given
        // 2028-05-12T22:37:48Z
        val certificate = "MIICXDCCAcWgAwIBAgIBADANBgkqhkiG9w0BAQ0FADBLMQswCQYDVQQGEwJlczESMBAGA1UECAwJQ2F0YWxvbmlhMREwDwYDVQQKDAhEb29yZGVjazEVMBMGA1UEAwwMZG9vcmRlY2suY29tMB4XDTI1MDUxMzIyMzc0OFoXDTI4MDUxMjIyMzc0OFowSzELMAkGA1UEBhMCZXMxEjAQBgNVBAgMCUNhdGFsb25pYTERMA8GA1UECgwIRG9vcmRlY2sxFTATBgNVBAMMDGRvb3JkZWNrLmNvbTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA30CdHL4m9jRN2ic2Pj5UHtWTaP89jP1BlIIyhvDb7c1bMXqJ04NYF8RQRP/LNuA34I7xJHO0MzGNhChuhu/SZpGT9XHj77iCh+WQyed6eyh+5h0qmToVIJbdLpb+z4dKERLR9NUp/zGn8G27fQmVU2JhRkRsnFtJUFFD6Z6O4IkCAwEAAaNQME4wHQYDVR0OBBYEFAw0TCqXK8ylRrW/uzLobHwiCGqUMB8GA1UdIwQYMBaAFAw0TCqXK8ylRrW/uzLobHwiCGqUMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQENBQADgYEAOrVU9zixzNfPG+6gHOFl0Ptmjj3jBud1BXW2B6uieBbnhRWcqp2WUv4EBMMNeGzLsai3XqDXrDWi5DCThtogWRsjrcdCwlUNKAfE/BQuC+WlsoWPwQix53keFazcaA3A6nozxwLeoLn1mfSGNjjLLCJC4/NLLemUKM6dCaSUM10="

        // When
        val result = certificate.isCertificateInvalidOrExpired()

        // Then
        assertFalse { result }
    }
}