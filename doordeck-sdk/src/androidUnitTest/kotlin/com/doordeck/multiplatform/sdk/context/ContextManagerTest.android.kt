package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_EXPIRED_CERTIFICATE
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_VALID_JWT
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUri
import com.doordeck.multiplatform.sdk.randomUuid
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.util.toUri
import kotlinx.coroutines.test.runTest
import java.security.KeyPair
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
        val apiEnvironment = ApiEnvironment.entries.random()
        val fusionHost = randomUri()
        val cloudAuthToken = randomString()
        val cloudRefreshToken = randomString()
        val fusionAuthToken = randomString()
        val userId = randomUuid()
        val email = randomEmail()
        val certificateChain = listOf(PLATFORM_TEST_VALID_CERTIFICATE)
        val keyPair = CryptoManager.generateKeyPair()
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
            setKeyPair(keyPair)
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
        assertContentEquals(certificateChain, ContextManager.getCertificateChain())
        assertEquals(keyPair.public, ContextManager.getKeyPair()?.public)
        assertEquals(keyPair.private, ContextManager.getKeyPair()?.private)
        assertTrue { ContextManager.isKeyPairVerified() }
        assertEquals(cloudAuthToken, ContextManager.getCloudAuthToken())
        assertEquals(cloudRefreshToken, ContextManager.getCloudRefreshToken())
        assertEquals(fusionAuthToken, ContextManager.getFusionAuthToken())
        assertEquals(fusionHost, ContextManager.getFusionHost())
    }

    @Test
    fun shouldClearContext() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.entries.random()
        val fusionHost = randomUri()
        val cloudAuthToken = randomString()
        val cloudRefreshToken = randomString()
        val fusionAuthToken = randomString()
        val userId = randomUuid()
        val email = randomEmail()
        val certificateChain = listOf(PLATFORM_TEST_VALID_CERTIFICATE)
        val keyPair = CryptoManager.generateKeyPair()
        val keyPairVerified = keyPair.public
        ContextManager.apply {
            setApiEnvironment(apiEnvironment)
            setCloudAuthToken(cloudAuthToken)
            setCloudRefreshToken(cloudRefreshToken)
            setFusionHost(fusionHost)
            setFusionAuthToken(fusionAuthToken)
            setUserId(userId)
            setCertificateChain(certificateChain)
            setKeyPair(keyPair)
            setKeyPairVerified(keyPairVerified)
            setUserEmail(email)
        }

        // When
        ContextManager.clearContext()

        // Then
        assertEquals(ApiEnvironment.PROD, ContextManager.getApiEnvironment())
        assertNull(ContextManager.getUserId())
        assertNull(ContextManager.getUserEmail())
        assertNull(ContextManager.getCertificateChain())
        assertNull(ContextManager.getKeyPair())
        assertFalse { ContextManager.isKeyPairVerified() }
        assertNull(ContextManager.getCloudAuthToken())
        assertNull(ContextManager.getCloudRefreshToken())
        assertNull(ContextManager.getFusionAuthToken())
        assertEquals(DEFAULT_FUSION_HOST.toUri(), ContextManager.getFusionHost())
    }

    @Test
    fun shouldStoreOperationContext() = runTest {
        // Given
        val userId = randomUuid()
        val certificateChain = listOf(PLATFORM_TEST_VALID_CERTIFICATE)
        val keyPair = CryptoManager.generateKeyPair()
        val settings = DefaultSecureStorage(MemorySettings())
        Context.setSecureStorageImpl(settings)
        ContextManager.setOperationContext(userId, certificateChain, keyPair, true)

        // When
        Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        ContextManager.clearContext()
        Context.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(userId, ContextManager.getUserId())
        assertContentEquals(certificateChain, ContextManager.getCertificateChain())
        assertEquals(keyPair.public, ContextManager.getKeyPair()?.public)
        assertEquals(keyPair.private, ContextManager.getKeyPair()?.private)
    }

    @Test
    fun shouldCheckAuthTokenNullValidity() = runTest {
        // Given
        ContextManager.clearContext()

        // When
        val result = ContextManager.isCloudAuthTokenInvalidOrExpired()

        // Then
        assertTrue { result }
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
        val publicKey = CryptoManager.generateKeyPair().public
        val privateKey = CryptoManager.generateKeyPair().private
        ContextManager.setKeyPair(KeyPair(publicKey, privateKey))

        // When
        val result = ContextManager.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidity() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setKeyPair(keyPair)

        // When
        val result = ContextManager.isKeyPairValid()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldUpdateApiEnvironment() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.STAGING

        // When
        ContextManager.setApiEnvironment(apiEnvironment)

        // Then
        assertEquals(apiEnvironment, ContextManager.getApiEnvironment())
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalid() = runTest {
        // Given
        ContextManager.setCloudAuthToken(randomString())

        // When
        val result = ContextManager.getContextState()

        // Then
        assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, result)
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalid() = runTest {
        // Given
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)

        // When
        val result = ContextManager.getContextState()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_INVALID, result)
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerified() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)

        // When
        val result = ContextManager.getContextState()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED, result)
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalid() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(listOf(PLATFORM_TEST_EXPIRED_CERTIFICATE))

        // When
        val result = ContextManager.getContextState()

        // Then
        assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED, result)
    }

    @Test
    fun shouldGetContextStateReady() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT)
        ContextManager.setKeyPair(keyPair)
        ContextManager.setKeyPairVerified(keyPair.public)
        ContextManager.setCertificateChain(listOf(PLATFORM_TEST_VALID_CERTIFICATE))

        // When
        val result = ContextManager.getContextState()

        // Then
        assertEquals(ContextState.READY, result)
    }
}