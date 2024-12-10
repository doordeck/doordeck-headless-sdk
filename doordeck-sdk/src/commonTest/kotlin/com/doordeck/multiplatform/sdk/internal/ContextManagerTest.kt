package com.doordeck.multiplatform.sdk.internal

import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.uuid.Uuid

class ContextManagerTest {

    @Test
    fun shouldStoreAndLoadContext() = runTest {
        // Given
        val cloudAuthToken = Uuid.random().toString()
        val cloudRefreshToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        ContextManagerImpl.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        ContextManagerImpl.setAuthToken(cloudAuthToken)
        ContextManagerImpl.setRefreshToken(cloudRefreshToken)
        ContextManagerImpl.setFusionAuthToken(fusionAuthToken)
        ContextManagerImpl.setOperationContext(userId, certificateChain, publicKey, privateKey)

        // When
        ContextManagerImpl.storeContext()
        ContextManagerImpl.reset()
        ContextManagerImpl.loadContext()

        // Then
        assertEquals(userId, ContextManagerImpl.getUserId())
        assertContentEquals(certificateChain, ContextManagerImpl.getCertificateChain())
        assertContentEquals(publicKey, ContextManagerImpl.getPublicKey())
        assertContentEquals(privateKey, ContextManagerImpl.getPrivateKey())
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
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        ContextManagerImpl.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        ContextManagerImpl.setAuthToken(cloudAuthToken)
        ContextManagerImpl.setRefreshToken(cloudRefreshToken)
        ContextManagerImpl.setFusionAuthToken(fusionAuthToken)
        ContextManagerImpl.setOperationContext(userId, certificateChain, publicKey, privateKey)
        ContextManagerImpl.storeContext()

        // When
        ContextManagerImpl.clearContext()
        ContextManagerImpl.reset()
        ContextManagerImpl.loadContext()

        // Then
        assertNull(ContextManagerImpl.getUserId())
        assertNull(ContextManagerImpl.getCertificateChain())
        assertNull(ContextManagerImpl.getPublicKey())
        assertNull(ContextManagerImpl.getPrivateKey())
        assertNull(ContextManagerImpl.getAuthToken())
        assertNull(ContextManagerImpl.getRefreshToken())
        assertNull(ContextManagerImpl.getFusionAuthToken())
    }
}