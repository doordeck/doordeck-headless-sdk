package com.doordeck.multiplatform.sdk.internal

import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull
import kotlin.uuid.Uuid

class ContextManagerTest {

    @Test
    fun shouldStoreAndLoadContext() = runTest {
        // Given
        val cloudAuthToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        contextManager.setAuthToken(cloudAuthToken)
        contextManager.setFusionAuthToken(fusionAuthToken)
        contextManager.setOperationContext(userId, certificateChain, publicKey, privateKey)

        // When
        contextManager.storeContext()
        contextManager.reset()
        contextManager.loadContext()

        // Then
        val restored = contextManager.getOperationContext()
        assertEquals(userId, restored.userId)
        assertContentEquals(certificateChain, restored.userCertificateChain)
        assertContentEquals(privateKey, restored.userPrivateKey)
        assertEquals(cloudAuthToken, contextManager.currentToken)
        assertEquals(fusionAuthToken, contextManager.currentFusionToken)
    }

    @Test
    fun shouldClearContext() = runTest {
        // Given
        val cloudAuthToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        contextManager.setAuthToken(cloudAuthToken)
        contextManager.setFusionAuthToken(fusionAuthToken)
        contextManager.setOperationContext(userId, certificateChain, publicKey, privateKey)
        contextManager.storeContext()

        // When
        contextManager.clearContext()
        contextManager.reset()
        contextManager.loadContext()

        // Then
        assertFails {
            contextManager.getOperationContext()
        }
        assertNull(contextManager.currentToken)
        assertNull(contextManager.currentFusionToken)
    }
}