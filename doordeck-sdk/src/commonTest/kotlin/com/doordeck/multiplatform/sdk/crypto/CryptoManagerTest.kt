package com.doordeck.multiplatform.sdk.crypto

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.verifySignature
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CryptoManagerTest {

    private val JAVA_PUBLIC_KEY = "MCowBQYDK2VwAyEA2E8RSaHnxBpnr+RGneGfpdLFhYEPVldzHx1TxuuyjD8="
    private val JAVA_PRIVATE_KEY = "MC4CAQAwBQYDK2VwBCIEIIgEWMf5XswAhA4SwRRNl8IH34+4329pQKBfwWPVtFat"

    private val SODIUM_PUBLIC_KEY = "9vG/XmIT0JxFjjWmXzd5F7XVbWLBeIPWb2qHKnjSz8o="
    private val SODIUM_PRIVATE_KEY = "OLPBsJ3zReQj2r2YNgnGMn/B8SW/U7qP9hMrd0mdP9j28b9eYhPQnEWONaZfN3kXtdVtYsF4g9ZvaocqeNLPyg=="

    private val CRYPTO_KIT_PUBLIC_KEY = "VpXka4JVjIYQ969Yqo92+x4JgwZPh0QiJIKx/3XzAxs="
    private val CRYPTO_KIT_PRIVATE_KEY = "GJsHlbSK/tdAGDL5+7QjB/aJx/AfKOWjMUGOpQ/1F9U="

    private val BOUNCY_CASTLE_PUBLIC_KEY = "MCowBQYDK2VwAyEAUoW8fvEIAw8cW+gx6TWh27NDzRvO9k/++9YsyN8xWsU="
    private val BOUNCY_CASTLE_PRIVATE_KEY = "MFECAQEwBQYDK2VwBCIEIOmntkLRdLIVT+BDWRF88R3Bu3XIrl3PR5U2mPmlyD74gSEAUoW8fvEIAw8cW+gx6TWh27NDzRvO9k/++9YsyN8xWsU="

    @Test
    fun shouldGenerateCryptoKeyPair() = runTest {
        CryptoManager.generateKeyPair()
    }

    @Test
    fun shouldSignWithPrivateKey() = runTest {
        // Given
        val content = "hello"
        val keyPair = CryptoManager.generateKeyPair()

        // When
        val result = content.signWithPrivateKey(keyPair.private)

        // Then
        assertTrue { result.verifySignature(keyPair.public, content) }
    }

    @Test
    fun shouldSignWithBouncyCastlePrivateKey() = runTest {
        // Given
        val content = "hello"

        // Then
        val result = content.signWithPrivateKey(BOUNCY_CASTLE_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            result.verifySignature(BOUNCY_CASTLE_PUBLIC_KEY.decodeBase64ToByteArray(), content)
        }
    }

    @Test
    fun shouldVerifyWithBouncyCastlePrivateKey() = runTest {
        // Given
        val signed = "vpdhVsz6zrQsbOFxBnslQGgmZ3rc/Jp6KgdMvoe9/azRz9CRqlsap1SO4Adouhs/Tw+AONYurPKbp1/MLgZqDg=="
        val content = "hello"

        // Then
        val result = signed.decodeBase64ToByteArray().verifySignature(BOUNCY_CASTLE_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldFailToVerifyWithBouncyCastlePrivateKey() = runTest {
        // Given
        val signed = "vpdhVsz6zrQsbOFxBnsQQGgmZ3rc/Jp6KgdMvoe9/azRz9CRqlsap1SO4Adouhs/Tw+AONYurPKbp1/MLgZqDg=="
        val content = "hello"

        // Then
        val result = signed.decodeBase64ToByteArray().verifySignature(BOUNCY_CASTLE_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldSignWithJavaPrivateKey() = runTest {
        // Given
        val content = "hello"

        // Then
        val result = content.signWithPrivateKey(JAVA_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            result.verifySignature(JAVA_PUBLIC_KEY.decodeBase64ToByteArray(), content)
        }
    }

    @Test
    fun shouldVerifyWithJavaPrivateKey() = runTest {
        // Given
        val signed = "HC/uACBwVVbDGkFm96raXEEkSB3EfgPoQw3UFA1IKgkGZdRMtpZKgP3ocg7iAvGk7ggYvO8qPn+q75ufyuP0AA=="
        val content = "hello"

        // Then
        val result = signed.decodeBase64ToByteArray().verifySignature(JAVA_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldFailToVerifyWithJavaPrivateKey() = runTest {
        // Given
        val signed = "HC/uACBwVVbDGkFm96raXEkkSB3EfgPoQw3UFA1IKgkGZdRMtpZKgP3ocg7iAvGk7ggYvO8qPn+q75ufyuP0AA=="
        val content = "hello"

        // Then
        val result = signed.decodeBase64ToByteArray().verifySignature(JAVA_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldSignWithLibsodiumPrivateKey() = runTest {
        // Given
        val content = "hello"

        // When
        val result = content.signWithPrivateKey(SODIUM_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            result.verifySignature(SODIUM_PUBLIC_KEY.decodeBase64ToByteArray(), content)
        }
    }

    @Test
    fun shouldVerifyWithLibsodiumPrivateKey() = runTest {
        // Given
        val signed = "oFCuWDPrOimX1yrptCq/6wg8SdlPJnnLwBURMhHp7wY3WSbgWbrc14+X5VS5CoRuBDbA/HvusfXx49YcUJbjCg=="
        val content = "hello"

        // When
        val result = signed.decodeBase64ToByteArray().verifySignature(SODIUM_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldFailToVerifyWithLibsodiumPrivateKey() = runTest {
        // Given
        val signed = "FFCuWDPrOimX1yrptCq/6wg8SdlPJnnLwBURMhHp7wY3WSbgWbrc14+X5VS5CoRuBDbA/HvusfXx49YcUJbjCg=="
        val content = "hello"

        // When
        val result = signed.decodeBase64ToByteArray().verifySignature(SODIUM_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldSignWithCryptoKitPrivateKey() = runTest {
        // Given
        val content = "hello"

        // When
        val result = content.signWithPrivateKey(CRYPTO_KIT_PRIVATE_KEY.decodeBase64ToByteArray())

        // Then
        assertTrue {
            result.verifySignature(CRYPTO_KIT_PUBLIC_KEY.decodeBase64ToByteArray(), content)
        }
    }

    @Test
    fun shouldVerifyWithCryptoKitPrivateKey() = runTest {
        // Given
        val signed = "nIv3lnqaUTc4u4VDyujr2BmsYDAMNvwGiutRGbMTril/B8tzZHvwsWtn03SgoQ3VuzyZ2ATq0CtNsRUdoI5UAg=="
        val content = "hello"

        // When
        val result = signed.decodeBase64ToByteArray().verifySignature(CRYPTO_KIT_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldFailToVerifyWithCryptoKitPrivateKey() = runTest {
        // Given
        val signed = "IIv3lnqaUTc4u4VDyujr2BmsYDAMNvwGiutRGbMTril/B8tzZHvwsWtn03SgoQ3VuzyZ2ATq0CtNsRUdoI5UAg=="
        val content = "hello"

        // When
        val result = signed.decodeBase64ToByteArray().verifySignature(CRYPTO_KIT_PUBLIC_KEY.decodeBase64ToByteArray(), content)

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldVerifyExpireDateFuture() = runTest {
        // Given
        val certificate = "MIIDADCCAeigAwIBAgIGAZOS7Z/dMA0GCSqGSIb3DQEBCwUAMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTAeFw0yNDEyMDQxODI3NDZaFw00NDExMjkxODI3NDZaMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKVUdDhA08HC4GycerNe2PdHre6tZfviddT3L9vGalTVjBl+5VBfsKieg8tgfYANh1tfDEBzIfRkm2/AQ8hUK+0f8oAzo/utgM9r9LR1oadU6WTX8UiEpkzBVjpazh05dMDZhZyNRpfWLgGcqQ/rzMkHAeD3XG2FcYvy35HFnMMtPcSVhYWwKaeoBB0fiiGaYdkJz2YiZN35jPMCB83uBgxiaPkTfK47S9pZz89tztvMGq9oXewOS2rxIljECcGEB3kQlWRWOVvw5nZIKzv2TxPoXWZKBfRcL/nTgURaiCACD3m+rNYHzkWgdXUo4ICA9WB7wzFkPdjv0oyl65r9SQcCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAlVSckJ68fF1l70TvUrMUUMooD64q3dX3rEsoKcQg6vY9gwzKNUAjIh0wTllF1RmtNux9NUm0wEhWqT6f3EpQAnYTFxhmF3nyop7O2ybFDXmW6lCppVKTMQuacgPMZwEZbaPNEeznCZloGnoJw7WFdLm3sC0+KGagEn96kaYe/ZzxE8iA1Jj0OZu+4UggkmdQ0LqWHnWiSvqNzlC806kVrg47E6RmmTh/gMCykbEHTGX3cyAiT0VGjDkvMdvINEx2dwHDCFh9Szm3raC4a0g0S+EknATwUIrZhuIhOch0aXH+m/Oove286mr27tesqN6ZEw6j5hKO5Jj7KnS9Hon7BA=="

        // When
        val result = CryptoManager.isCertificateInvalidOrExpired(certificate)

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldVerifyExpireDateExpired() = runTest {
        // Given
        val certificate = "MIIDADCCAeigAwIBAgIGAZOS8A/NMA0GCSqGSIb3DQEBCwUAMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTAeFw0yNDEyMDQxODMwMjZaFw0yNDEyMDUxODMwMjZaMEExDjAMBgNVBAMMBXRlc3RzMQswCQYDVQQGEwJVUzEiMCAGCSqGSIb3DQEJARYTYmVybmF0QGRvb3JkZWNrLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMIdLEAhDBz8cpqN9I8KNpVrdHmcobNqmHxpN36KmJ7G5K5i7Uv3SPKfrLw2zvf15+VPkvxDIeciegRkEfW5p0rjJy0ecKudi7kSmQYxKs8m/PMYmT/0qHpx+I9i3Uz/Wln7mvFIvQi+855USMgh37N6kruBO1bBDCSpM/yS0WC3szdmTG4QHxFRqlDkzGuLDhT+thM1mzhRhwTuL/+B9C3Z4SP/wd+lWQXeOtwRIZgjUrLbczwdVUeIR5tG4bV46y2OSMvwTrFVa87ZkcBpOzkVXsK4oMpujYJsoMJyOcRPjdFfpZC2B6tcQhI5p31/SrWPE6Y8M1/ctgqkks/80ykCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAlID39SmMzMMwpUHpDLAspsIXXG6z9AxPSGWY5h8vgaMukUSPZ8rKHbTBU29UrIOw3vzcapn06dDlQtaWLiatjHM3RK6JxMEciggWTr5G3EkM0FV4IBVVJXFyED9HW9wW9MZjijVN5LC+5yV50qZifUt/LwCyS6QZJ/mry2WQ/LZeNmt5VjPKAnP8CrQHa/m4My0rbxglJkjm5Bsh4J+tOp7wDgUZQqyCD0iQ6tUBx+lfKW4wwJFO9EAXz73oH+DHXDT1gTwWaqVVonJBeRsgXfGf7bWe1h8iNBCnXYOMf4qJcwJYADYab1GnpAlscn++KSXJtwWoWXSOTet6DRtf9A=="

        // When
        val result = CryptoManager.isCertificateInvalidOrExpired(certificate)

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldVerifyExpireDateInvalid() = runTest {
        // Given
        val certificate = "IAMACERTIFICATE"

        // When
        val result = CryptoManager.isCertificateInvalidOrExpired(certificate)

        // Then
        assertTrue { result }
    }
}