package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomByteArray
import com.doordeck.multiplatform.sdk.storage.migrations.Migrate0To1
import com.doordeck.multiplatform.sdk.storage.migrations.Migrate1To2
import com.doordeck.multiplatform.sdk.storage.migrations.Migrations
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.russhwolf.settings.contains
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MigrationTest {

    @Test
    fun shouldMigrate0To1Test() = runTest {
        // Given
        Migrations.overrideMigrations(listOf(Migrate0To1))
        val deprecatedKey = "KEY_PAIR_VERIFIED"
        val newKey = "KEY_PAIR_VERIFIED_KEY"
        val verified = randomBoolean()
        val settings = MemorySettings().apply {
            putBoolean(deprecatedKey, verified)
        }

        // When
        val storage = DefaultSecureStorage(settings)

        // Then
        assertEquals(1, storage.getStorageVersion())
        assertFalse { settings.contains(deprecatedKey) }
        assertTrue { settings.contains(newKey) }
        assertEquals(verified, settings.getBooleanOrNull(newKey))
    }

    @Test
    fun shouldMigrate1To2Test() = runTest {
        // Given
        Migrations.overrideMigrations(listOf(Migrate1To2))
        val deprecatedKey = "KEY_PAIR_VERIFIED_KEY"
        val newKey = "VERIFIED_KEY_PAIR_KEY"
        val key = randomByteArray().encodeByteArrayToBase64()
        val settings = MemorySettings().apply {
            putBoolean(deprecatedKey, true)
            putString("PUBLIC_KEY_KEY", key)
        }

        // When
        val storage = DefaultSecureStorage(settings)

        // Then
        assertEquals(2, storage.getStorageVersion())
        assertFalse { settings.contains(deprecatedKey) }
        assertTrue { settings.contains(newKey) }
        assertEquals(key, settings.getStringOrNull(newKey))
    }

    @Test
    fun shouldNotRunMigrationsTest() = runTest {
        // Given
        Migrations.overrideMigrations(listOf(Migrate0To1))
        val key = "STORAGE_VERSION_KEY"
        val newKey = "KEY_PAIR_VERIFIED_KEY"
        val settings = MemorySettings().apply {
            putInt(key, 1)
        }

        // When
        val storage = DefaultSecureStorage(settings)

        // Then
        assertEquals(1, storage.getStorageVersion())
        assertFalse { settings.contains(newKey) }
    }
}