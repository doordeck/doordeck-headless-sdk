package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Context
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
        val cloudAuthToken = Uuid.random().toString()
        val cloudRefreshToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val email = "${Uuid.random()}@doordeck.com"
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        ContextManagerImpl.setAuthToken(cloudAuthToken)
        ContextManagerImpl.setRefreshToken(cloudRefreshToken)
        ContextManagerImpl.setFusionAuthToken(fusionAuthToken)
        ContextManagerImpl.setUserId(userId)
        ContextManagerImpl.setCertificateChain(certificateChain)
        ContextManagerImpl.setKeyPair(publicKey, privateKey)
        ContextManagerImpl.setUserEmail(email)

        // When
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
        ContextManagerImpl.setAuthToken(cloudAuthToken)
        ContextManagerImpl.setRefreshToken(cloudRefreshToken)
        ContextManagerImpl.setFusionAuthToken(fusionAuthToken)
        ContextManagerImpl.setUserId(userId)
        ContextManagerImpl.setCertificateChain(certificateChain)
        ContextManagerImpl.setKeyPair(publicKey, privateKey)
        ContextManagerImpl.setUserEmail(email)

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
        ContextManagerImpl.setOperationContext(userId, certificateChain, publicKey, privateKey)

        // When
        ContextManagerImpl.reset()
        ContextManagerImpl.loadContext()

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
        ContextManagerImpl.setOperationContextJson(operationContextData.toJson())

        // When
        ContextManagerImpl.reset()
        ContextManagerImpl.loadContext()

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
        val result = ContextManagerImpl.isAuthTokenAboutToExpire()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckCertificateChainNullValidity() = runTest {
        // Given
        ContextManagerImpl.reset()

        // When
        val result = ContextManagerImpl.isCertificateChainAboutToExpire()

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