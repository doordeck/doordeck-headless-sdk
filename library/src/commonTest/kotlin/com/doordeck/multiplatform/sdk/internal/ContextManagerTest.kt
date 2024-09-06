package com.doordeck.multiplatform.sdk.internal

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.util.toArrayList
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import com.russhwolf.settings.MapSettings
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull

class ContextManagerTest {


    @Test
    fun shouldTestContextManager() {
        LibsodiumInitializer.initializeWithCallback {  }

        // Given - shouldTestStore
        val cloudAuthToken = uuid4().toString()
        val fusionAuthToken = uuid4().toString()
        val userId = uuid4().toString()
        val certificateChain = (1..3).map { uuid4().toString() }.toArrayList()
        val privateKey = uuid4().toString().encodeToByteArray()
        val contextManager = ContextManagerImpl()
        contextManager.setSecureStorageImpl(DefaultSecureStorage(MapSettings()))
        contextManager.setAuthToken(cloudAuthToken)
        contextManager.setFusionAuthToken(fusionAuthToken)
        contextManager.setOperationContext(userId, certificateChain, privateKey)
        contextManager.storeContext()

        contextManager.reset() // Remove the context from the memory

        // Then
        contextManager.loadContext()
        val restored = contextManager.getOperationContext()
        assertEquals(userId, restored.userId)
        assertContentEquals(certificateChain, restored.userCertificateChain)
        assertContentEquals(privateKey, restored.userPrivateKey)
        assertEquals(cloudAuthToken, contextManager.currentToken)
        assertEquals(fusionAuthToken, contextManager.currentFusionToken)

        contextManager.reset() // Remove the context from the memory
        contextManager.clearContext() // Remove the context from the secure storage

        contextManager.loadContext()

        assertFails {
            contextManager.getOperationContext()
        }
        assertNull(contextManager.currentToken)
        assertNull(contextManager.currentFusionToken)
    }
}