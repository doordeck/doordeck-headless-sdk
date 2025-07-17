package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
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
        Context.apply {
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
        Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        Context.reset()
        Context.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(apiEnvironment, Context.getApiEnvironment())
        assertEquals(userId, Context.getUserId())
        assertEquals(email, Context.getUserEmail())
        assertContentEquals(certificateChain, Context.getCertificateChain())
        assertContentEquals(publicKey, Context.getPublicKey())
        assertContentEquals(privateKey, Context.getPrivateKey())
        assertContentEquals(publicKey, Context.getKeyPair()?.public)
        assertContentEquals(privateKey, Context.getKeyPair()?.private)
        assertTrue { Context.isKeyPairVerified() }
        assertEquals(cloudAuthToken, Context.getCloudAuthToken())
        assertEquals(cloudRefreshToken, Context.getCloudRefreshToken())
        assertEquals(fusionAuthToken, Context.getFusionAuthToken())
        assertEquals(fusionHost, Context.getFusionHost())
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
        Context.apply {
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
        Context.clearContext()
        Context.reset()

        // Then
        assertEquals(ApiEnvironment.PROD, Context.getApiEnvironment())
        assertNull(Context.getUserId())
        assertNull(Context.getUserEmail())
        assertNull(Context.getCertificateChain())
        assertNull(Context.getPublicKey())
        assertNull(Context.getPrivateKey())
        assertNull(Context.getKeyPair())
        assertFalse { Context.isKeyPairVerified() }
        assertNull(Context.getCloudAuthToken())
        assertNull(Context.getCloudRefreshToken())
        assertNull(Context.getFusionAuthToken())
        assertEquals(DEFAULT_FUSION_HOST, Context.getFusionHost())
    }

    @Test
    fun shouldStoreOperationContext() = runTest {
        // Given
        val userId = Uuid.random().toString()
        val certificateChain = (1..3).map { Uuid.random().toString() }
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        val settings = DefaultSecureStorage(MemorySettings())
        Context.setSecureStorageImpl(settings)
        Context.setOperationContext(userId, certificateChain, publicKey, privateKey, true)

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

    @Test
    fun shouldCheckAuthTokenNullValidity() = runTest {
        // Given
        Context.reset()

        // When
        val result = Context.isCloudAuthTokenInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckCertificateChainNullValidity() = runTest {
        // Given
        Context.reset()

        // When
        val result = Context.isCertificateChainInvalidOrExpired()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldCheckKeyPairNullValidity() = runTest {
        // Given
        Context.reset()

        // When
        val result = Context.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidityWithInvalidKeys() = runTest {
        // Given
        val publicKey = Uuid.random().toString().encodeToByteArray()
        val privateKey = Uuid.random().toString().encodeToByteArray()
        Context.setKeyPair(publicKey, privateKey)

        // When
        val result = Context.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidityWithNonMatchingKeys() = runTest {
        // Given
        val publicKey = CryptoManager.generateRawKeyPair().public
        val privateKey = CryptoManager.generateRawKeyPair().private
        Context.setKeyPair(publicKey, privateKey)

        // When
        val result = Context.isKeyPairValid()

        // Then
        assertFalse { result }
    }

    @Test
    fun shouldCheckKeyPairValidity() = runTest {
        // Given
        val keyPair = CryptoManager.generateRawKeyPair()
        Context.setKeyPair(keyPair.public, keyPair.private)

        // When
        val result = Context.isKeyPairValid()

        // Then
        assertTrue { result }
    }

    @Test
    fun shouldUpdateApiEnvironment() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.STAGING

        // When
        Context.setApiEnvironment(apiEnvironment)

        // Then
        assertEquals(apiEnvironment, Context.getApiEnvironment())
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalid() = runTest {
        // Given
        Context.setCloudAuthToken(randomString())

        // When
        val result = Context.getContextState()

        // Then
        assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, result)
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalid() = runTest {
        // Given
        Context.setCloudAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA")

        // When
        val result = Context.getContextState()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_INVALID, result)
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerified() = runTest {
        // Given
        val keyPair = CryptoManager.generateRawKeyPair()
        Context.setCloudAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA")
        Context.setKeyPair(keyPair.public, keyPair.private)

        // When
        val result = Context.getContextState()

        // Then
        assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED, result)
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalid() = runTest {
        // Given
        val keyPair = CryptoManager.generateRawKeyPair()
        Context.setCloudAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA")
        Context.setKeyPair(keyPair.public, keyPair.private)
        Context.setKeyPairVerified(keyPair.public)
        Context.setCertificateChain(listOf(Uuid.random().toString()))

        // When
        val result = Context.getContextState()

        // Then
        assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED, result)
    }

    @Test
    fun shouldGetContextStateReady() = runTest {
        // Given
        val keyPair = CryptoManager.generateRawKeyPair()
        Context.setCloudAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjQwMzhmODE5MmZmMTZiMGQ4N2E3OWYyZjFlOTYyZWIwIn0.eyJleHAiOiIyNTUzNzczMjYxIn0.0O36vfj7QasI-PkE3qEqg4Vm1lF4vGxmmJzso7zLp23qmljOuBd6NaknPG9ZxxIo5WEbaJrgN8zAuRxtA8sYzA")
        Context.setKeyPair(keyPair.public, keyPair.private)
        Context.setKeyPairVerified(keyPair.public)
        Context.setCertificateChain(listOf("MIICVDCCAb2gAwIBAgIBADANBgkqhkiG9w0BAQ0FADBHMQswCQYDVQQGEwJlczESMBAGA1UECAwJQmFyY2Vsb25hMREwDwYDVQQKDAhEb29yZGVjazERMA8GA1UEAwwIRG9vcmRlY2swHhcNMjUwNzA4MjA0NzI4WhcNMjcwNzA4MjA0NzI4WjBHMQswCQYDVQQGEwJlczESMBAGA1UECAwJQmFyY2Vsb25hMREwDwYDVQQKDAhEb29yZGVjazERMA8GA1UEAwwIRG9vcmRlY2swgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKG708ApCnAtbuJZzlgfqK8K39JPbb5Mrd1RRkGCJ6B6LV9nDudu53sUCxt8WT/xWIF3KUThbLNOqX3ZDN/agJbJTeh/T62TxiWmWE3LFyrqz2xQ7Jpfg807Hj0KPjJxdfpY6Btg+RP2+rHzjbvYsu950/l2ymfuU3xMYt/OSvUrAgMBAAGjUDBOMB0GA1UdDgQWBBQsomoEUylwegTctRzxee21JVD4xTAfBgNVHSMEGDAWgBQsomoEUylwegTctRzxee21JVD4xTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBDQUAA4GBACrKh/QepnoYS7YVIjmUkoPGyZHsm0Rww5R00cryat5uhpt8Fp3IZ1PTJ83OHl1P6yl6bATZTTls Mc0Tk9GLTLMZl0qh4gXevU8jpGTi2Dv9AqKmTio1m8va5EgQfhkT/efnxQPQfCyvxo/j4xah2uBdGDtlwm2q+XF7ClZyAZBO"))

        // When
        val result = Context.getContextState()

        // Then
        assertEquals(ContextState.READY, result)
    }
}