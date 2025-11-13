package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_EXPIRED_CERTIFICATE
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_VALID_JWT
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.jsArrayOf
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomPublicKey
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUrlString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.requestHistory
import com.doordeck.multiplatform.sdk.responseHistory
import com.doordeck.multiplatform.sdk.setupMockClient
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.js.collections.toList
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ContextManagerTest : IntegrationTest() {

    @Test
    fun shouldStoreAndLoadContext() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.entries.random().name
        val fusionHost = randomUrlString()
        val cloudAuthToken = randomString()
        val cloudRefreshToken = randomString()
        val fusionAuthToken = randomString()
        val userId = randomUuidString()
        val email = randomEmail()
        val certificateChain = jsArrayOf(PLATFORM_TEST_VALID_CERTIFICATE)
        val keyPair = CryptoManager.generateRawKeyPair()
        val keyPairVerified = keyPair.public
        val settings = DefaultSecureStorage(MemorySettings())
        Context.setSecureStorageImpl(settings)
        ContextManager.apply {
            setApiEnvironment(apiEnvironment)
            setCloudAuthToken(cloudAuthToken)
            setCloudRefreshToken(cloudRefreshToken)
            setFusionHost(fusionHost)
            setFusionAuthToken(fusionAuthToken)
            setUserId(userId)
            setCertificateChain(certificateChain)
            setKeyPair(keyPair.public, keyPair.private)
            setKeyPairVerified(keyPairVerified)
            setUserEmail(email)
        }

        // When
        Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        ContextManager.clearContext()
        Context.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(apiEnvironment, ContextManager.getApiEnvironment())
        assertEquals(userId, ContextManager.getUserId())
        assertEquals(email, ContextManager.getUserEmail())
        assertContentEquals(certificateChain.toList(), ContextManager.getCertificateChain()?.toList())
        assertContentEquals(keyPair.public, ContextManager.getKeyPair()?.public)
        assertContentEquals(keyPair.private, ContextManager.getKeyPair()?.private)
        assertTrue { ContextManager.isKeyPairVerified() }
        assertEquals(cloudAuthToken, ContextManager.getCloudAuthToken())
        assertEquals(cloudRefreshToken, ContextManager.getCloudRefreshToken())
        assertEquals(fusionAuthToken, ContextManager.getFusionAuthToken())
        assertEquals(fusionHost, ContextManager.getFusionHost())
    }

    @Test
    fun shouldClearContext() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.entries.random().name
        val fusionHost = randomUrlString()
        val cloudAuthToken = randomString()
        val cloudRefreshToken = randomString()
        val fusionAuthToken = randomString()
        val userId = randomUuidString()
        val email = randomEmail()
        val certificateChain = jsArrayOf(PLATFORM_TEST_VALID_CERTIFICATE)
        val keyPair = CryptoManager.generateRawKeyPair()
        val keyPairVerified = keyPair.public
        ContextManager.apply {
            setApiEnvironment(apiEnvironment)
            setCloudAuthToken(cloudAuthToken)
            setCloudRefreshToken(cloudRefreshToken)
            setFusionHost(fusionHost)
            setFusionAuthToken(fusionAuthToken)
            setUserId(userId)
            setCertificateChain(certificateChain)
            setKeyPair(keyPair.public, keyPair.private)
            setKeyPairVerified(keyPairVerified)
            setUserEmail(email)
        }

        // When
        ContextManager.clearContext()

        // Then
        assertEquals(ApiEnvironment.PROD.name, ContextManager.getApiEnvironment())
        assertNull(ContextManager.getUserId())
        assertNull(ContextManager.getUserEmail())
        assertNull(ContextManager.getCertificateChain())
        assertNull(ContextManager.getKeyPair())
        assertFalse { ContextManager.isKeyPairVerified() }
        assertNull(ContextManager.getCloudAuthToken())
        assertNull(ContextManager.getCloudRefreshToken())
        assertNull(ContextManager.getFusionAuthToken())
        assertEquals(DEFAULT_FUSION_HOST, ContextManager.getFusionHost())
    }

    @Test
    fun shouldStoreOperationContext() = runTest {
        // Given
        val userId = randomUuidString()
        val certificateChain = jsArrayOf(PLATFORM_TEST_VALID_CERTIFICATE)
        val keyPair = CryptoManager.generateRawKeyPair()
        val settings = DefaultSecureStorage(MemorySettings())
        Context.setSecureStorageImpl(settings)
        ContextManager.setOperationContext(userId, certificateChain, keyPair.public, keyPair.private, true)

        // When
        Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        ContextManager.clearContext()
        Context.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(userId, ContextManager.getUserId())
        assertContentEquals(certificateChain.toList(), ContextManager.getCertificateChain()?.toList())
        assertContentEquals(keyPair.public, ContextManager.getKeyPair()?.public)
        assertContentEquals(keyPair.private, ContextManager.getKeyPair()?.private)
    }

    @Test
    fun shouldCheckAuthTokenValidity() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpired(true).await()

        // Then
        assertFalse { result }
        assertEquals(1, CloudHttpClient.client.requestHistory().size)
        assertEquals(1, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldCheckAuthTokenValidityWithoutServerCheck() = runTest {
        // Given
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        CloudHttpClient.setupMockClient(null)

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpired(false).await()

        // Then
        assertFalse { result }
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldCheckAuthTokenInvalidity() = runTest {
        // Given
        ContextManager.setCloudAuthToken(randomString())
        CloudHttpClient.setupMockClient(null)

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpired(true).await()

        // Then
        assertTrue { result }
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldCheckAuthTokenInvalidityWithoutServerCheck() = runTest {
        // Given
        ContextManager.setCloudAuthToken(randomString())
        CloudHttpClient.setupMockClient(null)

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpired(false).await()

        // Then
        assertTrue { result }
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldCheckAuthTokenNullValidity() = runTest {
        // Given
        ContextManager.clearContext()
        CloudHttpClient.setupMockClient(null)

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpired(true).await()

        // Then
        assertTrue { result }
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldCheckAuthTokenNullValidityWithoutServerCheck() = runTest {
        // Given
        ContextManager.clearContext()
        CloudHttpClient.setupMockClient(null)

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpired(false).await()

        // Then
        assertTrue { result }
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldCheckCertificateChainNullValidity() = runTest {
        // Given
        ContextManager.clearContext()

        // When
        val result = ContextManager.isCertificateChainInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckKeyPairNullValidity() = runTest {
        // Given
        ContextManager.clearContext()

        // When
        val result = ContextManager.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidityWithInvalidKeys() = runTest {
        // Given
        val publicKey = randomString().encodeToByteArray()
        val privateKey = randomString().encodeToByteArray()
        Context.setKeyPair(publicKey, privateKey)

        // When
        val result = ContextManager.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidityWithNonMatchingKeys() = runTest {
        // Given
        val publicKey = CryptoManager.generateRawKeyPair().public
        val privateKey = CryptoManager.generateRawKeyPair().private
        ContextManager.setKeyPair(publicKey, privateKey)

        // When
        val result = ContextManager.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidity() = runTest {
        // Given
        val keyPair = CryptoManager.generateRawKeyPair()
        ContextManager.setKeyPair(keyPair.public, keyPair.private)

        // When
        val result = ContextManager.isKeyPairValid()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldUpdateApiEnvironment() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.STAGING.name

        // When
        ContextManager.setApiEnvironment(apiEnvironment)

        // Then
        assertEquals(apiEnvironment, ContextManager.getApiEnvironment())
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalid() = runTest {
        // Given
        CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(randomString())

        // When
        val result = ContextManager.getContextState(true).await()

        // Then
        assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED.name, result)
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalidWithoutServerCheck() = runTest {
        // Given
        CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(randomString())

        // When
        val result = ContextManager.getContextState(false).await()

        // Then
        assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED.name, result)
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalid() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)

        // When
        val result = ContextManager.getContextState(true).await()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_INVALID.name, result)
        assertEquals(1, CloudHttpClient.client.requestHistory().size)
        assertEquals(1, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalidWithoutServerCheck() = runTest {
        // Given
        CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)

        // When
        val result = ContextManager.getContextState(false).await()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_INVALID.name, result)
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerified() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        val keyPair = CryptoManager.generateRawKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair.public, keyPair.private)

        // When
        val result = ContextManager.getContextState(true).await()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED.name, result)
        assertEquals(1, CloudHttpClient.client.requestHistory().size)
        assertEquals(1, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerifiedWithoutServerCheck() = runTest {
        // Given
        CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair.public, keyPair.private)

        // When
        val result = ContextManager.getContextState(false).await()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED.name, result)
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalid() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        val keyPair = CryptoManager.generateRawKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair.public, keyPair.private)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(jsArrayOf(PLATFORM_TEST_EXPIRED_CERTIFICATE))

        // When
        val result = ContextManager.getContextState(true).await()

        // Then
        assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED.name, result)
        assertEquals(1, CloudHttpClient.client.requestHistory().size)
        assertEquals(1, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalidWithoutServerCheck() = runTest {
        // Given
        CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair.public, keyPair.private)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(jsArrayOf(PLATFORM_TEST_EXPIRED_CERTIFICATE))

        // When
        val result = ContextManager.getContextState(false).await()

        // Then
        assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED.name, result)
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateReady() = runTest {
        // Given
        CloudHttpClient.setupMockClient(BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomString(),
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        ))
        val keyPair = CryptoManager.generateRawKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair.public, keyPair.private)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(jsArrayOf(PLATFORM_TEST_VALID_CERTIFICATE))

        // When
        val result = ContextManager.getContextState(true).await()

        // Then
        assertEquals(ContextState.READY.name, result)
        assertEquals(1, CloudHttpClient.client.requestHistory().size)
        assertEquals(1, CloudHttpClient.client.responseHistory().size)
    }

    @Test
    fun shouldGetContextStateReadyWithoutServerCheck() = runTest {
        // Given
        CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair.public, keyPair.private)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(jsArrayOf(PLATFORM_TEST_VALID_CERTIFICATE))

        // When
        val result = ContextManager.getContextState(false).await()

        // Then
        assertEquals(ContextState.READY.name, result)
        assertEquals(0, CloudHttpClient.client.requestHistory().size)
        assertEquals(0, CloudHttpClient.client.responseHistory().size)
    }
}