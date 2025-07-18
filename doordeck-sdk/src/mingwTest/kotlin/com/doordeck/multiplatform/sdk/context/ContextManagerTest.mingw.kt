package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.model.data.OperationContextData
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.uuid.Uuid

class ContextManagerTest : IntegrationTest() {

    @Test
    fun shouldStoreJsonOperationContext() = runTest {
        // Given
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val operationContextData = OperationContextData(
            userId = userId,
            userCertificateChain = certificateChain.certificateChainToString(),
            userPublicKey = publicKey.encodeByteArrayToBase64(),
            userPrivateKey = privateKey.encodeByteArrayToBase64()
        )
        val settings = DefaultSecureStorage(MemorySettings())
        Context.setSecureStorageImpl(settings)
        ContextManager.setOperationContextJson(operationContextData.toJson())

        // When
        Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        Context.reset()
        Context.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(userId, Context.getUserId())
        assertContentEquals(certificateChain, Context.getCertificateChain())
        assertContentEquals(publicKey, Context.getPublicKey())
        assertContentEquals(privateKey, Context.getPrivateKey())
        assertContentEquals(publicKey, Context.getKeyPair()?.public)
        assertContentEquals(privateKey, Context.getKeyPair()?.private)
    }
}