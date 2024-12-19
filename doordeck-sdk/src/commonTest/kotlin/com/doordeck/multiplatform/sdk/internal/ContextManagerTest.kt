package com.doordeck.multiplatform.sdk.internal

import com.doordeck.multiplatform.sdk.api.model.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class ContextManagerTest {

    @Test
    fun shouldStoreAndLoadContext() = runTest {
        // Given
        val cloudAuthToken = Uuid.random().toString()
        val cloudRefreshToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val email = "${Uuid.random()}@doordeck.com"
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        ContextManagerImpl.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        ContextManagerImpl.setAuthToken(cloudAuthToken)
        ContextManagerImpl.setRefreshToken(cloudRefreshToken)
        ContextManagerImpl.setFusionAuthToken(fusionAuthToken)
        ContextManagerImpl.setUserId(userId)
        ContextManagerImpl.setCertificateChain(certificateChain)
        ContextManagerImpl.setKeyPair(publicKey, privateKey)
        ContextManagerImpl.setUserEmail(email)

        // When
        ContextManagerImpl.storeContext()
        ContextManagerImpl.reset()
        ContextManagerImpl.loadContext()

        // Then
        assertEquals(userId, ContextManagerImpl.getUserId())
        assertEquals(email, ContextManagerImpl.getUserEmail())
        assertContentEquals(certificateChain, ContextManagerImpl.getCertificateChain())
        assertContentEquals(publicKey, ContextManagerImpl.getPublicKey())
        assertContentEquals(privateKey, ContextManagerImpl.getPrivateKey())
        assertContentEquals(publicKey, ContextManagerImpl.getKeyPair()?.public)
        assertContentEquals(privateKey, ContextManagerImpl.getKeyPair()?.private)
        assertEquals(cloudAuthToken, ContextManagerImpl.getAuthToken())
        assertEquals(cloudRefreshToken, ContextManagerImpl.getRefreshToken())
        assertEquals(fusionAuthToken, ContextManagerImpl.getFusionAuthToken())
    }

    @Test
    fun shouldClearContext() = runTest {
        // Given
        val cloudAuthToken = Uuid.random().toString()
        val cloudRefreshToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val email = "${Uuid.random()}@doordeck.com"
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        ContextManagerImpl.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        ContextManagerImpl.setAuthToken(cloudAuthToken)
        ContextManagerImpl.setRefreshToken(cloudRefreshToken)
        ContextManagerImpl.setFusionAuthToken(fusionAuthToken)
        ContextManagerImpl.setUserId(userId)
        ContextManagerImpl.setCertificateChain(certificateChain)
        ContextManagerImpl.setKeyPair(publicKey, privateKey)
        ContextManagerImpl.setUserEmail(email)
        ContextManagerImpl.storeContext()

        // When
        ContextManagerImpl.clearContext()
        ContextManagerImpl.reset()
        ContextManagerImpl.loadContext()

        // Then
        assertNull(ContextManagerImpl.getUserId())
        assertNull(ContextManagerImpl.getUserEmail())
        assertNull(ContextManagerImpl.getCertificateChain())
        assertNull(ContextManagerImpl.getPublicKey())
        assertNull(ContextManagerImpl.getPrivateKey())
        assertNull(ContextManagerImpl.getKeyPair())
        assertNull(ContextManagerImpl.getAuthToken())
        assertNull(ContextManagerImpl.getRefreshToken())
        assertNull(ContextManagerImpl.getFusionAuthToken())
    }

    @Test
    fun shouldStoreOperationContext() = runTest {
        // Given
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        contextManager.setOperationContext(userId, certificateChain, publicKey, privateKey)

        // When
        contextManager.storeContext()
        contextManager.reset()
        contextManager.loadContext()

        // Then
        assertEquals(userId, contextManager.getUserId())
        assertContentEquals(certificateChain, contextManager.getCertificateChain())
        assertContentEquals(publicKey, contextManager.getPublicKey())
        assertContentEquals(privateKey, contextManager.getPrivateKey())
        assertContentEquals(publicKey, contextManager.getKeyPair()?.public)
        assertContentEquals(privateKey, contextManager.getKeyPair()?.private)
    }

    @Test
    fun shouldStoreJsonOperationContext() = runTest {
        // Given
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        val operationContextData = Context.OperationContextData(userId, certificateChain, publicKey.encodeByteArrayToBase64(), privateKey.encodeByteArrayToBase64())
        contextManager.setOperationContextJson(operationContextData.toJson())

        // When
        contextManager.storeContext()
        contextManager.reset()
        contextManager.loadContext()

        // Then
        assertEquals(userId, contextManager.getUserId())
        assertContentEquals(certificateChain, contextManager.getCertificateChain())
        assertContentEquals(publicKey, contextManager.getPublicKey())
        assertContentEquals(privateKey, contextManager.getPrivateKey())
        assertContentEquals(publicKey, contextManager.getKeyPair()?.public)
        assertContentEquals(privateKey, contextManager.getKeyPair()?.private)
    }

    @Test
    fun shouldCheckAuthTokenNullValidity() = runTest {
        // Given
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))

        // When
        val result = contextManager.isAuthTokenAboutToExpire()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckCertificateChainNullValidity() = runTest {
        // Given
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))

        // When
        val result = contextManager.isCertificateChainAboutToExpire()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckKeyPairNullValidity() = runTest {
        // Given
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))

        // When
        val result = contextManager.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairInvalidValidity() = runTest {
        // Given
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        contextManager.setKeyPair(publicKey, privateKey)

        // When
        val result = contextManager.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidity() = runTest {
        // Given
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        val keyPair = CryptoManager.generateKeyPair()
        contextManager.setKeyPair(keyPair.public, keyPair.private)

        // When
        val result = contextManager.isKeyPairValid()

        // Then
        assertTrue { result }
    }
}