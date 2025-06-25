package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Context
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class ContextManagerTest : IntegrationTest() {

    @Test
    fun shouldStoreAndLoadContext() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.entries.random()
        val cloudAuthToken = Uuid.random().toString()
        val cloudRefreshToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val email = "${Uuid.random()}@doordeck.com"
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val settings = DefaultSecureStorage(MemorySettings())
        ContextManagerImpl.apply {
            setSecureStorageImpl(settings)
            setApiEnvironment(apiEnvironment)
            setCloudAuthToken(cloudAuthToken)
            setCloudRefreshToken(cloudRefreshToken)
            setFusionAuthToken(fusionAuthToken)
            setUserId(userId)
            setCertificateChain(certificateChain)
            setKeyPair(publicKey, privateKey)
            setKeyPairVerified(publicKey)
            setUserEmail(email)
        }

        // When
        ContextManagerImpl.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        ContextManagerImpl.reset()
        ContextManagerImpl.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(apiEnvironment, ContextManagerImpl.getApiEnvironment())
        assertEquals(userId, ContextManagerImpl.getUserId())
        assertEquals(email, ContextManagerImpl.getUserEmail())
        assertContentEquals(certificateChain, ContextManagerImpl.getCertificateChain())
        assertContentEquals(publicKey, ContextManagerImpl.getPublicKey())
        assertContentEquals(privateKey, ContextManagerImpl.getPrivateKey())
        assertContentEquals(publicKey, ContextManagerImpl.getKeyPair()?.public)
        assertContentEquals(privateKey, ContextManagerImpl.getKeyPair()?.private)
        assertTrue { ContextManagerImpl.isKeyPairVerified() }
        assertEquals(cloudAuthToken, ContextManagerImpl.getCloudAuthToken())
        assertEquals(cloudRefreshToken, ContextManagerImpl.getCloudRefreshToken())
        assertEquals(fusionAuthToken, ContextManagerImpl.getFusionAuthToken())
    }

    @Test
    fun shouldClearContext() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.entries.random()
        val cloudAuthToken = Uuid.random().toString()
        val cloudRefreshToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val email = "${Uuid.random()}@doordeck.com"
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        ContextManagerImpl.apply {
            setApiEnvironment(apiEnvironment)
            setCloudAuthToken(cloudAuthToken)
            setCloudRefreshToken(cloudRefreshToken)
            setFusionAuthToken(fusionAuthToken)
            setUserId(userId)
            setCertificateChain(certificateChain)
            setKeyPair(publicKey, privateKey)
            setKeyPairVerified(publicKey)
            setUserEmail(email)
        }

        // When
        ContextManagerImpl.clearContext()
        ContextManagerImpl.reset()

        // Then
        assertEquals(ApiEnvironment.PROD, ContextManagerImpl.getApiEnvironment())
        assertNull(ContextManagerImpl.getUserId())
        assertNull(ContextManagerImpl.getUserEmail())
        assertNull(ContextManagerImpl.getCertificateChain())
        assertNull(ContextManagerImpl.getPublicKey())
        assertNull(ContextManagerImpl.getPrivateKey())
        assertNull(ContextManagerImpl.getKeyPair())
        assertFalse { ContextManagerImpl.isKeyPairVerified() }
        assertNull(ContextManagerImpl.getCloudAuthToken())
        assertNull(ContextManagerImpl.getCloudRefreshToken())
        assertNull(ContextManagerImpl.getFusionAuthToken())
    }

    @Test
    fun shouldStoreOperationContext() = runTest {
        // Given
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val settings = DefaultSecureStorage(MemorySettings())
        ContextManagerImpl.setSecureStorageImpl(settings)
        ContextManagerImpl.setOperationContext(userId, certificateChain, publicKey, privateKey)

        // When
        ContextManagerImpl.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        ContextManagerImpl.reset()
        ContextManagerImpl.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(userId, ContextManagerImpl.getUserId())
        assertContentEquals(certificateChain, ContextManagerImpl.getCertificateChain())
        assertContentEquals(publicKey, ContextManagerImpl.getPublicKey())
        assertContentEquals(privateKey, ContextManagerImpl.getPrivateKey())
        assertContentEquals(publicKey, ContextManagerImpl.getKeyPair()?.public)
        assertContentEquals(privateKey, ContextManagerImpl.getKeyPair()?.private)
    }

    @Test
    fun shouldStoreJsonOperationContext() = runTest {
        // Given
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val operationContextData = Context.OperationContextData(userId, certificateChain.certificateChainToString(), publicKey.encodeByteArrayToBase64(), privateKey.encodeByteArrayToBase64())
        val settings = DefaultSecureStorage(MemorySettings())
        ContextManagerImpl.setSecureStorageImpl(settings)
        ContextManagerImpl.setOperationContextJson(operationContextData.toJson())

        // When
        ContextManagerImpl.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        ContextManagerImpl.reset()
        ContextManagerImpl.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(userId, ContextManagerImpl.getUserId())
        assertContentEquals(certificateChain, ContextManagerImpl.getCertificateChain())
        assertContentEquals(publicKey, ContextManagerImpl.getPublicKey())
        assertContentEquals(privateKey, ContextManagerImpl.getPrivateKey())
        assertContentEquals(publicKey, ContextManagerImpl.getKeyPair()?.public)
        assertContentEquals(privateKey, ContextManagerImpl.getKeyPair()?.private)
    }

    @Test
    fun shouldCheckAuthTokenNullValidity() = runTest {
        // Given
        ContextManagerImpl.reset()

        // When
        val result = ContextManagerImpl.isCloudAuthTokenInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckCertificateChainNullValidity() = runTest {
        // Given
        ContextManagerImpl.reset()

        // When
        val result = ContextManagerImpl.isCertificateChainInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckKeyPairNullValidity() = runTest {
        // Given
        ContextManagerImpl.reset()

        // When
        val result = ContextManagerImpl.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairInvalidValidity() = runTest {
        // Given
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        ContextManagerImpl.setKeyPair(publicKey, privateKey)

        // When
        val result = ContextManagerImpl.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidity() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()
        ContextManagerImpl.setKeyPair(keyPair.public, keyPair.private)

        // When
        val result = ContextManagerImpl.isKeyPairValid()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldUpdateApiEnvironment() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.STAGING

        // When
        ContextManagerImpl.setApiEnvironment(apiEnvironment)

        // Then
        assertEquals(apiEnvironment, ContextManagerImpl.getApiEnvironment())
    }
}