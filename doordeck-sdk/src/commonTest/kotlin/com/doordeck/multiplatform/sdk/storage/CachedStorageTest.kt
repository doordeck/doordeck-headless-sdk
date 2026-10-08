package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.randomByteArray
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUrlString
import com.doordeck.multiplatform.sdk.randomUuidString
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class CachedStorageTest {

    @Test
    fun shouldInsertAndLoadAllKeysWithoutReadingDelegate() {
        // Given
        val delegate = CountingSecureStorage()
        val storage = CachedStorage(delegate)
        val values = StoredValues.random()

        // When
        values.insertInto(storage)

        // Then
        values.assertLoadedFrom(storage)
        assertEquals(0, delegate.reads)
        values.assertLoadedFrom(delegate)
    }

    @Test
    fun shouldLoadAllKeysFromDelegateOnlyOnce() {
        // Given
        val delegate = CountingSecureStorage()
        val storage = CachedStorage(delegate)
        val values = StoredValues.random()
        values.insertInto(delegate)

        // When
        repeat(3) { values.assertLoadedFrom(storage) }

        // Then
        assertEquals(StoredValues.KEYS, delegate.reads)
    }

    @Test
    fun shouldCacheMissingValuesForAllKeys() {
        // Given
        val delegate = CountingSecureStorage()
        val storage = CachedStorage(delegate)

        // When
        repeat(3) { assertAllKeysMissing(storage) }

        // Then
        assertEquals(StoredValues.KEYS, delegate.reads)
    }

    @Test
    fun shouldOverrideCachedValuesForAllKeysOnInsert() {
        // Given
        val delegate = CountingSecureStorage()
        val storage = CachedStorage(delegate)
        StoredValues.random().insertInto(storage)
        val values = StoredValues.random()

        // When
        values.insertInto(storage)

        // Then
        values.assertLoadedFrom(storage)
        values.assertLoadedFrom(delegate)
    }

    @Test
    fun shouldRemoveCachedKeyPairVerifiedOnNullInsert() {
        // Given
        val delegate = CountingSecureStorage()
        val storage = CachedStorage(delegate)
        storage.setKeyPairVerified(randomByteArray())

        // When
        storage.setKeyPairVerified(null)

        // Then
        assertNull(storage.getKeyPairVerified())
        assertNull(delegate.getKeyPairVerified())
    }

    @Test
    fun shouldReloadAllKeysFromDelegateAfterClear() {
        // Given
        val delegate = CountingSecureStorage()
        val storage = CachedStorage(delegate)
        StoredValues.random().insertInto(storage)

        // When
        storage.clear()

        // Then
        assertAllKeysMissing(storage)
        assertEquals(StoredValues.KEYS, delegate.reads)
    }

    @Test
    fun shouldReloadFromDelegateAfterFailedWrite() {
        // Given
        val token = randomString()
        val delegate = CountingSecureStorage().apply { addCloudAuthToken(token) }
        val storage = CachedStorage(delegate)
        storage.getCloudAuthToken()
        delegate.failWrites = true

        // When
        assertFailsWith<IllegalStateException> { storage.addCloudAuthToken(randomString()) }

        // Then
        assertEquals(token, storage.getCloudAuthToken())
        assertEquals(2, delegate.reads)
    }

    @Test
    fun shouldNotExposeCachedByteArrays() {
        // Given
        val privateKey = randomByteArray()
        val expected = privateKey.copyOf()
        val storage = CachedStorage(CountingSecureStorage())
        storage.addPrivateKey(privateKey)

        // When
        privateKey.fill(0)
        storage.getPrivateKey()?.fill(0)

        // Then
        assertContentEquals(expected, storage.getPrivateKey())
    }

    private fun assertAllKeysMissing(storage: SecureStorage) {
        assertNull(storage.getApiEnvironment())
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

    private class StoredValues(
        val apiEnvironment: ApiEnvironment,
        val cloudAuthToken: String,
        val cloudRefreshToken: String,
        val fusionHost: String,
        val fusionAuthToken: String,
        val publicKey: ByteArray,
        val privateKey: ByteArray,
        val keyPairVerified: ByteArray,
        val userId: String,
        val userEmail: String,
        val certificateChain: List<String>
    ) {

        fun insertInto(storage: SecureStorage) {
            storage.setApiEnvironment(apiEnvironment)
            storage.addCloudAuthToken(cloudAuthToken)
            storage.addCloudRefreshToken(cloudRefreshToken)
            storage.setFusionHost(fusionHost)
            storage.addFusionAuthToken(fusionAuthToken)
            storage.addPublicKey(publicKey)
            storage.addPrivateKey(privateKey)
            storage.setKeyPairVerified(keyPairVerified)
            storage.addUserId(userId)
            storage.addUserEmail(userEmail)
            storage.addCertificateChain(certificateChain)
        }

        fun assertLoadedFrom(storage: SecureStorage) {
            assertEquals(apiEnvironment, storage.getApiEnvironment())
            assertEquals(cloudAuthToken, storage.getCloudAuthToken())
            assertEquals(cloudRefreshToken, storage.getCloudRefreshToken())
            assertEquals(fusionHost, storage.getFusionHost())
            assertEquals(fusionAuthToken, storage.getFusionAuthToken())
            assertContentEquals(publicKey, storage.getPublicKey())
            assertContentEquals(privateKey, storage.getPrivateKey())
            assertContentEquals(keyPairVerified, storage.getKeyPairVerified())
            assertEquals(userId, storage.getUserId())
            assertEquals(userEmail, storage.getUserEmail())
            assertEquals(certificateChain, storage.getCertificateChain())
        }

        companion object {

            val KEYS = StorageKey.entries.size - 1

            fun random() = StoredValues(
                apiEnvironment = ApiEnvironment.entries.random(),
                cloudAuthToken = randomString(),
                cloudRefreshToken = randomString(),
                fusionHost = randomUrlString(),
                fusionAuthToken = randomString(),
                publicKey = randomByteArray(),
                privateKey = randomByteArray(),
                keyPairVerified = randomByteArray(),
                userId = randomUuidString(),
                userEmail = randomEmail(),
                certificateChain = List(3) { randomString() }
            )
        }
    }

    /**
     * In-memory [SecureStorage] that counts the number of reads and can be configured to fail on writes.
     */
    private class CountingSecureStorage(
        private val storage: SecureStorage = DefaultSecureStorage(MemorySettings())
    ) : SecureStorage by storage {

        var reads = 0
            private set
        var failWrites = false

        private fun <T> read(block: () -> T): T = block().also { reads++ }

        private fun write(block: () -> Unit) {
            check(!failWrites) { "Write failed" }
            block()
        }

        override fun getApiEnvironment() = read { storage.getApiEnvironment() }
        override fun getCloudAuthToken() = read { storage.getCloudAuthToken() }
        override fun getCloudRefreshToken() = read { storage.getCloudRefreshToken() }
        override fun getFusionHost() = read { storage.getFusionHost() }
        override fun getFusionAuthToken() = read { storage.getFusionAuthToken() }
        override fun getPublicKey() = read { storage.getPublicKey() }
        override fun getPrivateKey() = read { storage.getPrivateKey() }
        override fun getKeyPairVerified() = read { storage.getKeyPairVerified() }
        override fun getUserId() = read { storage.getUserId() }
        override fun getUserEmail() = read { storage.getUserEmail() }
        override fun getCertificateChain() = read { storage.getCertificateChain() }
        override fun addCloudAuthToken(token: String) = write { storage.addCloudAuthToken(token) }
    }
}
