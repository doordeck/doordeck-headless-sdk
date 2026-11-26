package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.randomByteArray
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.storage.StorageKey.STORAGE_VERSION_KEY
import com.doordeck.multiplatform.sdk.storage.migrations.Migrations
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class DefaultSecureStorageTest {

    @Test
    fun shouldClearOnlyTheSdkKeys() {
        // Given
        val hostKey = "host-application-key"
        val hostValue = randomString()
        val settings = MemorySettings().apply {
            putString(hostKey, hostValue)
        }
        val storage = DefaultSecureStorage(settings).apply {
            addCloudAuthToken(randomString())
            addCloudRefreshToken(randomString())
            addFusionAuthToken(randomString())
            addPublicKey(randomByteArray())
            addPrivateKey(randomByteArray())
            setKeyPairVerified(randomByteArray())
            addUserId(randomUuidString())
            addUserEmail(randomEmail())
            addCertificateChain(listOf(randomString()))
        }
        val storageVersion = storage.getStorageVersion()

        // When
        storage.clear()

        // Then
        assertNull(storage.getCloudAuthToken())
        assertNull(storage.getCloudRefreshToken())
        assertNull(storage.getFusionAuthToken())
        assertNull(storage.getPublicKey())
        assertNull(storage.getPrivateKey())
        assertNull(storage.getKeyPairVerified())
        assertNull(storage.getUserId())
        assertNull(storage.getUserEmail())
        assertNull(storage.getCertificateChain())

        // Then - the host application's value and the storage version are untouched
        assertEquals(hostValue, settings.getStringOrNull(hostKey))
        assertEquals(storageVersion, storage.getStorageVersion())
        assertEquals(setOf(hostKey, STORAGE_VERSION_KEY.name), settings.keys)
    }

    @Test
    fun shouldNotRerunMigrationsAfterClearing() {
        // Given
        val settings = MemorySettings()
        val storage = DefaultSecureStorage(settings)
        val latestVersion = Migrations.migrations.maxOf { it.toVersion }
        assertEquals(latestVersion, storage.getStorageVersion())

        // When
        storage.clear()

        // Then - a new instance over the same settings is already at the latest version
        assertEquals(latestVersion, DefaultSecureStorage(settings).getStorageVersion())
        assertFalse(settings.keys.any { it != STORAGE_VERSION_KEY.name })
    }
}
