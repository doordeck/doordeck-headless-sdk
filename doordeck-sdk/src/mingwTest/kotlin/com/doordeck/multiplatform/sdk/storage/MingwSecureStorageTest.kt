package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.randomByteArray
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.randomUrlString
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cstr
import kotlinx.cinterop.nativeHeap
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

private val setApiEnvironmentCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["apiEnvironment"] = ptr.toKString()
}
private val setCloudAuthTokenCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["cloudAuthToken"] = ptr.toKString()
}
private val setCloudRefreshTokenCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["cloudRefreshToken"] = ptr.toKString()
}
private val setFusionHostCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["fusionHost"] = ptr.toKString()
}
private val setFusionAuthTokenCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["fusionAuthToken"] = ptr.toKString()
}
private val setPublicKeyCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["publicKey"] = ptr.toKString()
}
private val setPrivateKeyCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["privateKey"] = ptr.toKString()
}
private val setKeyPairVerifiedCb = staticCFunction<CPointer<ByteVar>?, Unit> { ptr ->
    if (ptr != null) {
        backingStore["keyPairVerified"] = ptr.toKString()
    } else {
        backingStore.remove("keyPairVerified")
    }
}
private val setUserIdCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["userId"] = ptr.toKString()
}
private val setUserEmailCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["userEmail"] = ptr.toKString()
}
private val setCertificateChainCb = staticCFunction<CPointer<ByteVar>, Unit> { ptr ->
    backingStore["certificateChain"] = ptr.toKString()
}

private val getApiEnvironmentCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["apiEnvironment"]?.cstr?.place(nativeHeap.allocArray(backingStore["apiEnvironment"]!!.length + 1))
}
private val getCloudAuthTokenCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["cloudAuthToken"]?.cstr?.place(nativeHeap.allocArray(backingStore["cloudAuthToken"]!!.length + 1))
}
private val getCloudRefreshTokenCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["cloudRefreshToken"]?.cstr?.place(nativeHeap.allocArray(backingStore["cloudRefreshToken"]!!.length + 1))
}
private val getFusionHostCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["fusionHost"]?.cstr?.place(nativeHeap.allocArray(backingStore["fusionHost"]!!.length + 1))
}
private val getFusionAuthTokenCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["fusionAuthToken"]?.cstr?.place(nativeHeap.allocArray(backingStore["fusionAuthToken"]!!.length + 1))
}
private val getPublicKeyCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["publicKey"]?.cstr?.place(nativeHeap.allocArray(backingStore["publicKey"]!!.length + 1))
}
private val getPrivateKeyCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["privateKey"]?.cstr?.place(nativeHeap.allocArray(backingStore["privateKey"]!!.length + 1))
}
private val getKeyPairVerifiedCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["keyPairVerified"]?.cstr?.place(nativeHeap.allocArray(backingStore["keyPairVerified"]!!.length + 1))
}
private val getUserIdCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["userId"]?.cstr?.place(nativeHeap.allocArray(backingStore["userId"]!!.length + 1))
}
private val getUserEmailCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["userEmail"]?.cstr?.place(nativeHeap.allocArray(backingStore["userEmail"]!!.length + 1))
}
private val getCertificateChainCb = staticCFunction<CPointer<ByteVar>?> {
    backingStore["certificateChain"]?.cstr?.place(nativeHeap.allocArray(backingStore["certificateChain"]!!.length + 1))
}

private val clearCb = staticCFunction<Unit> {
    backingStore.clear()
}

class MingwSecureStorageTest {

    private lateinit var storage: MingwSecureStorage

    @BeforeTest
    fun setUp() {
        backingStore.clear()
        storage = MingwSecureStorage(
            setApiEnvironmentCp = setApiEnvironmentCb,
            getApiEnvironmentCp = getApiEnvironmentCb,
            addCloudAuthTokenCp = setCloudAuthTokenCb,
            getCloudAuthTokenCp = getCloudAuthTokenCb,
            addCloudRefreshTokenCp = setCloudRefreshTokenCb,
            getCloudRefreshTokenCp = getCloudRefreshTokenCb,
            setFusionHostCp = setFusionHostCb,
            getFusionHostCp = getFusionHostCb,
            addFusionAuthTokenCp = setFusionAuthTokenCb,
            getFusionAuthTokenCp = getFusionAuthTokenCb,
            addPublicKeyCp = setPublicKeyCb,
            getPublicKeyCp = getPublicKeyCb,
            addPrivateKeyCp = setPrivateKeyCb,
            getPrivateKeyCp = getPrivateKeyCb,
            setKeyPairVerifiedCp = setKeyPairVerifiedCb,
            getKeyPairVerifiedCp = getKeyPairVerifiedCb,
            addUserIdCp = setUserIdCb,
            getUserIdCp = getUserIdCb,
            addUserEmailCp = setUserEmailCb,
            getUserEmailCp = getUserEmailCb,
            addCertificateChainCp = setCertificateChainCb,
            getCertificateChainCp = getCertificateChainCb,
            clearCp = clearCb
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
