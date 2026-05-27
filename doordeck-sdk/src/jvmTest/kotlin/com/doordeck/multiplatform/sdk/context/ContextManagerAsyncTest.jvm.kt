package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_EXPIRED_CERTIFICATE
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_VALID_JWT
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomPublicKey
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.requestHistory
import com.doordeck.multiplatform.sdk.responseHistory
import com.doordeck.multiplatform.sdk.setupMockClient
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ContextManagerAsyncTest : IntegrationTest() {

    @Test
    fun shouldCheckAuthTokenValidityAsync() = runTest {
        // Given
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        val client = CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))

        client.use {
            // When
            val result = ContextManager.isCloudAuthTokenInvalidOrExpiredAsync(true).await()

            // Then
            assertFalse { result }
            assertEquals(1, CloudHttpClient.client.requestHistory().size)
            assertEquals(1, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenValidityWithoutServerCheckAsync() = runTest {
        // Given
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        val client = CloudHttpClient.setupMockClient(null)

        client.use {
            // When
            val result = ContextManager.isCloudAuthTokenInvalidOrExpiredAsync(false).await()

            // Then
            assertFalse { result }
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenInvalidityAsync() = runTest {
        // Given
        ContextManager.setCloudAuthToken(randomString())
        val client = CloudHttpClient.setupMockClient(null)

        client.use {
            // When
            val result = ContextManager.isCloudAuthTokenInvalidOrExpiredAsync(true).await()

            // Then
            assertTrue { result }
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenInvalidityWithoutServerCheckAsync() = runTest {
        // Given
        ContextManager.setCloudAuthToken(randomString())
        val client = CloudHttpClient.setupMockClient(null)

        client.use {
            // When
            val result = ContextManager.isCloudAuthTokenInvalidOrExpiredAsync(false).await()

            // Then
            assertTrue { result }
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenNullValidityAsync() = runTest {
        // Given
        ContextManager.clearContext()
        val client = CloudHttpClient.setupMockClient(null)

        client.use {
            // When
            val result = ContextManager.isCloudAuthTokenInvalidOrExpiredAsync(true).await()

            // Then
            assertTrue { result }
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenNullValidityWithoutServerCheckAsync() = runTest {
        // Given
        ContextManager.clearContext()
        val client = CloudHttpClient.setupMockClient(null)

        client.use {
            // When
            val result = ContextManager.isCloudAuthTokenInvalidOrExpiredAsync(false).await()

            // Then
            assertTrue { result }
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalidAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(randomString())

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(true).await()

            // Then
            assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalidWithoutServerCheckAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(randomString())

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(false).await()

            // Then
            assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalidAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(true).await()

            // Then
            assertEquals(ContextState.KEY_PAIR_IS_INVALID, result)
            assertEquals(1, CloudHttpClient.client.requestHistory().size)
            assertEquals(1, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalidWithoutServerCheckAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(false).await()

            // Then
            assertEquals(ContextState.KEY_PAIR_IS_INVALID, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerifiedAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))

        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(true).await()

            // Then
            assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED, result)
        }
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerifiedWithoutServerCheckAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(false).await()

            // Then
            assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalidAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(listOf(PLATFORM_TEST_EXPIRED_CERTIFICATE))

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(true).await()

            // Then
            assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED, result)
        }
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalidWithoutServerCheckAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(listOf(PLATFORM_TEST_EXPIRED_CERTIFICATE))

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(false).await()

            // Then
            assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateReadyAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(listOf(PLATFORM_TEST_VALID_CERTIFICATE))

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(true).await()

            // Then
            assertEquals(ContextState.READY, result)
        }
    }

    @Test
    fun shouldGetContextStateReadyWithoutServerCheckAsync() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(listOf(PLATFORM_TEST_VALID_CERTIFICATE))

        client.use {
            // When
            val result = ContextManager.getContextStateAsync(false).await()

            // Then
            assertEquals(ContextState.READY, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }
}