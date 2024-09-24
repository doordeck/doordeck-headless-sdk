package com.doordeck.multiplatform.sdk.internal

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull

class ContextManagerTest {

    @Test
    fun shouldStoreAndLoadContext() = runTest {
        // Given
        val cloudAuthToken = uuid4().toString()
        val fusionAuthToken = uuid4().toString()
        val userId = uuid4().toString()
        val certificateChain = (1..3).map { uuid4().toString() }
        val privateKey = uuid4().toString().encodeToByteArray()
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        contextManager.setAuthToken(cloudAuthToken)
        contextManager.setFusionAuthToken(fusionAuthToken)
        contextManager.setOperationContext(userId, certificateChain, privateKey)

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
        val cloudAuthToken = uuid4().toString()
        val fusionAuthToken = uuid4().toString()
        val userId = uuid4().toString()
        val certificateChain = (1..3).map { uuid4().toString() }
        val privateKey = uuid4().toString().encodeToByteArray()
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        contextManager.setAuthToken(cloudAuthToken)
        contextManager.setFusionAuthToken(fusionAuthToken)
        contextManager.setOperationContext(userId, certificateChain, privateKey)
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