package com.doordeck.multiplatform.sdk.storage.migrations

import com.russhwolf.settings.Settings

/**
 * Interface for storage migrations.
 */
internal interface StorageMigration {

    /**
     * The minimum storage version required for the migration to execute.
     */
    val fromVersion: Int

    /**
     * The new storage version after migration.
     */
    val toVersion: Int

    /**
     * The function that holds the migration process.
     */
    fun migrate(settings: Settings)
}