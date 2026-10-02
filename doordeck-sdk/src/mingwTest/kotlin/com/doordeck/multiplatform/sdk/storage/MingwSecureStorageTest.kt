package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.randomByteArray
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.randomUrlString
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.set
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

private val backingStore = mutableMapOf<String, String>()

private val setEntryCb = staticCFunction<CPointer<ByteVar>, CPointer<ByteVar>?, Unit> { key, value ->
    val name = key.toKString()
    if (value == null) backingStore.remove(name) else backingStore[name] = value.toKString()
}

// Writes into the caller's buffer and reports the length, so the harness allocates nothing that the
// implementation under test would then have to free.
private val getEntryCb = staticCFunction<CPointer<ByteVar>, CPointer<ByteVar>?, Int, Int> { key, buffer, capacity ->
    val entry = backingStore[key.toKString()]
    if (entry == null) {
        -1
    } else {
        val bytes = entry.encodeToByteArray()
        if (buffer != null && capacity > bytes.size) {
            bytes.forEachIndexed { index, byte -> buffer[index] = byte }
            buffer[bytes.size] = 0
        }
        bytes.size
    }
}

private val clearEntriesCb = staticCFunction<Unit> {
    backingStore.clear()
}

class MingwSecureStorageTest {

    private lateinit var storage: MingwSecureStorage

    @BeforeTest
    fun setUp() {
        backingStore.clear()
        storage = MingwSecureStorage(
            setEntryCp = setEntryCb,
            getEntryCp = getEntryCb,
            clearEntriesCp = clearEntriesCb
        )
    }

    @Test
    fun shouldStoreAndRetrieveApiEnvironment() = runTest {
        // Given
        val environment = ApiEnvironment.entries.random()

        // When
        storage.setApiEnvironment(environment)

        // Then
        assertEquals(environment, storage.getApiEnvironment())
    }

    @Test
    fun shouldReturnNullForMissingApiEnvironment() = runTest {
        assertNull(storage.getApiEnvironment())
    }

    @Test
    fun shouldStoreAndRetrieveCloudAuthToken() = runTest {
        // Given
        val token = randomString()

        // When
        storage.addCloudAuthToken(token)

        // Then
        assertEquals(token, storage.getCloudAuthToken())
    }

    @Test
    fun shouldReturnNullForMissingCloudAuthToken() = runTest {
        assertNull(storage.getCloudAuthToken())
    }

    @Test
    fun shouldStoreAndRetrieveCloudRefreshToken() = runTest {
        // Given
        val token = randomString()

        // When
        storage.addCloudRefreshToken(token)

        // Then
        assertEquals(token, storage.getCloudRefreshToken())
    }

    @Test
    fun shouldReturnNullForMissingCloudRefreshToken() = runTest {
        assertNull(storage.getCloudRefreshToken())
    }

    @Test
    fun shouldStoreAndRetrieveFusionHost() = runTest {
        // Given
        val host = randomUrlString()

        // When
        storage.setFusionHost(host)

        // Then
        assertEquals(host, storage.getFusionHost())
    }

    @Test
    fun shouldReturnNullForMissingFusionHost() = runTest {
        assertNull(storage.getFusionHost())
    }

    @Test
    fun shouldStoreAndRetrieveFusionAuthToken() = runTest {
        // Given
        val token = randomString()

        // When
        storage.addFusionAuthToken(token)

        // Then
        assertEquals(token, storage.getFusionAuthToken())
    }

    @Test
    fun shouldReturnNullForMissingFusionAuthToken() = runTest {
        assertNull(storage.getFusionAuthToken())
    }

    @Test
    fun shouldStoreAndRetrievePublicKey() = runTest {
        // Given
        val key = randomByteArray()

        // When
        storage.addPublicKey(key)

        // Then
        assertContentEquals(key, storage.getPublicKey())
    }

    @Test
    fun shouldReturnNullForMissingPublicKey() = runTest {
        assertNull(storage.getPublicKey())
    }

    @Test
    fun shouldStoreAndRetrievePrivateKey() = runTest {
        // Given
        val key = randomByteArray()

        // When
        storage.addPrivateKey(key)

        // Then
        assertContentEquals(key, storage.getPrivateKey())
    }

    @Test
    fun shouldReturnNullForMissingPrivateKey() = runTest {
        assertNull(storage.getPrivateKey())
    }

    @Test
    fun shouldStoreAndRetrieveKeyPairVerified() = runTest {
        // Given
        val key = randomByteArray()

        // When
        storage.setKeyPairVerified(key)

        // Then
        assertContentEquals(key, storage.getKeyPairVerified())
    }

    @Test
    fun shouldStoreNullKeyPairVerified() = runTest {
        // Given
        val key = randomByteArray()
        storage.setKeyPairVerified(key)

        // When
        storage.setKeyPairVerified(null)

        // Then
        assertNull(storage.getKeyPairVerified())
    }

    @Test
    fun shouldReturnNullForMissingKeyPairVerified() = runTest {
        assertNull(storage.getKeyPairVerified())
    }

    @Test
    fun shouldStoreAndRetrieveUserId() = runTest {
        // Given
        val userId = randomUuidString()

        // When
        storage.addUserId(userId)

        // Then
        assertEquals(userId, storage.getUserId())
    }

    @Test
    fun shouldReturnNullForMissingUserId() = runTest {
        assertNull(storage.getUserId())
    }

    @Test
    fun shouldStoreAndRetrieveUserEmail() = runTest {
        // Given
        val email = randomEmail()

        // When
        storage.addUserEmail(email)

        // Then
        assertEquals(email, storage.getUserEmail())
    }

    @Test
    fun shouldReturnNullForMissingUserEmail() = runTest {
        assertNull(storage.getUserEmail())
    }

    @Test
    fun shouldStoreAndRetrieveCertificateChain() = runTest {
        // Given
        val chain = listOf(randomString(), randomString(), randomString())

        // When
        storage.addCertificateChain(chain)

        // Then
        assertEquals(chain, storage.getCertificateChain())
    }

    @Test
    fun shouldReturnNullForMissingCertificateChain() = runTest {
        assertNull(storage.getCertificateChain())
    }

    @Test
    fun shouldClearAllData() = runTest {
        // Given
        storage.addCloudAuthToken(randomString())
        storage.addCloudRefreshToken(randomString())
        storage.setFusionHost(randomUrlString())
        storage.addFusionAuthToken(randomString())
        storage.addPublicKey(randomByteArray())
        storage.addPrivateKey(randomByteArray())
        storage.setKeyPairVerified(randomByteArray())
        storage.addUserId(randomUuidString())
        storage.addUserEmail(randomEmail())
        storage.addCertificateChain(listOf(randomString()))

        // When
        storage.clear()

        // Then
        assertNull(storage.getCloudAuthToken())
        assertNull(storage.getCloudRefreshToken())
        assertNull(storage.getFusionHost())
        assertNull(storage.getFusionAuthToken())
        assertNull(storage.getPublicKey())
        assertNull(storage.getPrivateKey())
        assertNull(storage.getKeyPairVerified())
        assertNull(storage.getUserId())
        assertNull(storage.getUserEmail())
        assertNull(storage.getCertificateChain())
    }

    @Test
    fun shouldOverwriteExistingValues() = runTest {
        // Given
        val firstToken = randomString()
        val secondToken = randomString()

        // When
        storage.addCloudAuthToken(firstToken)
        storage.addCloudAuthToken(secondToken)

        // Then
        assertEquals(secondToken, storage.getCloudAuthToken())
    }

    @Test
    fun shouldRoundTripByteArraysThroughBase64() = runTest {
        // Given
        val publicKey = randomByteArray()
        val privateKey = randomByteArray()
        val verifiedKey = randomByteArray()

        // When
        storage.addPublicKey(publicKey)
        storage.addPrivateKey(privateKey)
        storage.setKeyPairVerified(verifiedKey)

        // Then
        assertTrue { backingStore["publicKey"]!!.isNotEmpty() }
        assertTrue { backingStore["privateKey"]!!.isNotEmpty() }
        assertTrue { backingStore["keyPairVerified"]!!.isNotEmpty() }
        assertContentEquals(publicKey, storage.getPublicKey())
        assertContentEquals(privateKey, storage.getPrivateKey())
        assertContentEquals(verifiedKey, storage.getKeyPairVerified())
    }

    @Test
    fun shouldStoreAllApiEnvironments() = runTest {
        for (env in ApiEnvironment.entries) {
            // When
            storage.setApiEnvironment(env)

            // Then
            assertEquals(env, storage.getApiEnvironment())
        }
    }
}