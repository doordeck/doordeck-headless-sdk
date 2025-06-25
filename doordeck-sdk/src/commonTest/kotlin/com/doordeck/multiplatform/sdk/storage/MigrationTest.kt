package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.randomBoolean
import com.russhwolf.settings.contains
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MigrationTest {

    @Test
    fun shouldMigrateStorageTest() = runTest {
        // Given
        val deprecatedKey = "KEY_PAIR_VERIFIED"
        val newKey = "KEY_PAIR_VERIFIED_KEY"
        val verified = randomBoolean()
        val settings = MemorySettings()
        val storage = DefaultSecureStorage(settings)
        settings.putBoolean(deprecatedKey, verified)

        // When
        storage.migrate()

        // Then
        assertEquals(1, storage.getStorageVersion())
        assertFalse { settings.contains(deprecatedKey) }
        assertTrue { settings.contains(newKey) }
        assertEquals(verified, settings.getBooleanOrNull(newKey))
    }

    @Test
    fun shouldNotRunMigrationsTest() = runTest {
        // Given
        val key = "STORAGE_VERSION_KEY"
        val newKey = "KEY_PAIR_VERIFIED_KEY"
        val settings = MemorySettings()
        val storage = DefaultSecureStorage(settings)
        settings.putInt(key, 1)

        // When
        storage.migrate()

        // Then
        assertEquals(1, storage.getStorageVersion())
        assertFalse { settings.contains(newKey) }
    }
}