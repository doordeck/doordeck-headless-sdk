package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Context
import com.doordeck.multiplatform.sdk.randomString
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
        val fusionHost = "https://${Uuid.random()}.com"
        val cloudAuthToken = Uuid.random().toString()
        val cloudRefreshToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val email = "${Uuid.random()}@doordeck.com"
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val keyPairVerified = publicKey
        val settings = DefaultSecureStorage(MemorySettings())
        ContextManagerImpl.apply {
            setSecureStorageImpl(settings)
            setApiEnvironment(apiEnvironment)
            setCloudAuthToken(cloudAuthToken)
            setCloudRefreshToken(cloudRefreshToken)
            setFusionHost(fusionHost)
            setFusionAuthToken(fusionAuthToken)
            setUserId(userId)
            setCertificateChain(certificateChain)
            setKeyPair(publicKey, privateKey)
            setKeyPairVerified(keyPairVerified)
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
        assertEquals(fusionHost, ContextManagerImpl.getFusionHost())
    }

    @Test
    fun shouldClearContext() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.entries.random()
        val fusionHost = "https://${Uuid.random()}.com"
        val cloudAuthToken = Uuid.random().toString()
        val cloudRefreshToken = Uuid.random().toString()
        val fusionAuthToken = Uuid.random().toString()
        val userId = Uuid.random().toString()
        val email = "${Uuid.random()}@doordeck.com"
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val keyPairVerified = publicKey
        ContextManagerImpl.apply {
            setApiEnvironment(apiEnvironment)
            setCloudAuthToken(cloudAuthToken)
            setCloudRefreshToken(cloudRefreshToken)
            setFusionHost(fusionHost)
            setFusionAuthToken(fusionAuthToken)
            setUserId(userId)
            setCertificateChain(certificateChain)
            setKeyPair(publicKey, privateKey)
            setKeyPairVerified(keyPairVerified)
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
        assertEquals(DEFAULT_FUSION_HOST, ContextManagerImpl.getFusionHost())
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
    fun shouldCheckKeyPairValidityWithInvalidKeys() = runTest {
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
    fun shouldCheckKeyPairValidityWithNonMatchingKeys() = runTest {
        // Given
        val publicKey = CryptoManager.generateKeyPair().public
        val privateKey = CryptoManager.generateKeyPair().private
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

    @Test
    fun shouldGetContextStateCloudTokenIsInvalid() = runTest {
        // Given
        ContextManagerImpl.setCloudAuthToken(randomString())

        // When
        val result = ContextManagerImpl.getContextState()

        // Then
        assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, result)
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalid() = runTest {
        // Given
        ContextManagerImpl.setCloudAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA")

        // When
        val result = ContextManagerImpl.getContextState()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_INVALID, result)
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerified() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()
        ContextManagerImpl.setCloudAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA")
        ContextManagerImpl.setKeyPair(keyPair.public, keyPair.private)

        // When
        val result = ContextManagerImpl.getContextState()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED, result)
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalid() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()
        ContextManagerImpl.setCloudAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA")
        ContextManagerImpl.setKeyPair(keyPair.public, keyPair.private)
        ContextManagerImpl.setKeyPairVerified(keyPair.public)
        ContextManagerImpl.setCertificateChain(listOf(Uuid.random().toString()))

        // When
        val result = ContextManagerImpl.getContextState()

        // Then
        assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED, result)
    }

    @Test
    fun shouldGetContextStateReady() = runTest {
        // Given
        val keyPair = CryptoManager.generateKeyPair()
        ContextManagerImpl.setCloudAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA")
        ContextManagerImpl.setKeyPair(keyPair.public, keyPair.private)
        ContextManagerImpl.setKeyPairVerified(keyPair.public)
        ContextManagerImpl.setCertificateChain(listOf("MIICVDCCAb2gAwIBAgIBADANBgkqhkiG9w0BAQ0FADBHMQswCQYDVQQGEwJlczESMBAGA1UECAwJQmFyY2Vsb25hMREwDwYDVQQKDAhEb29yZGVjazERMA8GA1UEAwwIRG9vcmRlY2swHhcNMjUwNzA4MjA0NzI4WhcNMjcwNzA4MjA0NzI4WjBHMQswCQYDVQQGEwJlczESMBAGA1UECAwJQmFyY2Vsb25hMREwDwYDVQQKDAhEb29yZGVjazERMA8GA1UEAwwIRG9vcmRlY2swgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKG708ApCnAtbuJZzlgfqK8K39JPbb5Mrd1RRkGCJ6B6LV9nDudu53sUCxt8WT/xWIF3KUThbLNOqX3ZDN/agJbJTeh/T62TxiWmWE3LFyrqz2xQ7Jpfg807Hj0KPjJxdfpY6Btg+RP2+rHzjbvYsu950/l2ymfuU3xMYt/OSvUrAgMBAAGjUDBOMB0GA1UdDgQWBBQsomoEUylwegTctRzxee21JVD4xTAfBgNVHSMEGDAWgBQsomoEUylwegTctRzxee21JVD4xTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBDQUAA4GBACrKh/QepnoYS7YVIjmUkoPGyZHsm0Rww5R00cryat5uhpt8Fp3IZ1PTJ83OHl1P6yl6bATZTTls Mc0Tk9GLTLMZl0qh4gXevU8jpGTi2Dv9AqKmTio1m8va5EgQfhkT/efnxQPQfCyvxo/j4xah2uBdGDtlwm2q+XF7ClZyAZBO"))

        // When
        val result = ContextManagerImpl.getContextState()

        // Then
        assertEquals(ContextState.READY, result)
    }
}