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
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpiredAsync().await()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckAuthTokenNullValidityAsync() = runTest {
        // Given
        ContextManager.clearContext()

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpiredAsync().await()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalidAsync() = runTest {
        // Given
        ContextManager.setCloudAuthToken(randomString())

        // When
        val result = ContextManager.getContextStateAsync().await()

        // Then
        assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, result)
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalidAsync() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)

        // When
        val result = ContextManager.getContextStateAsync().await()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_INVALID, result)
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerifiedAsync() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))

        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)

        // When
        val result = ContextManager.getContextStateAsync().await()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED, result)
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalidAsync() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
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

        // When
        val result = ContextManager.getContextStateAsync().await()

        // Then
        assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED, result)
    }

    @Test
    fun shouldGetContextStateReadyAsync() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
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

        // When
        val result = ContextManager.getContextStateAsync().await()

        // Then
        assertEquals(ContextState.READY, result)
    }
}