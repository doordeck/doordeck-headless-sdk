package com.doordeck.multiplatform.sdk.storage.migrations

import com.russhwolf.settings.Settings
import kotlin.jvm.JvmSynthetic

/**
 * Interface for storage migrations.
 */
internal interface StorageMigration {

    /**
     * The minimum storage version required for the migration to execute.
     */
    @get:JvmSynthetic
    val fromVersion: Int

    /**
     * The new storage version after migration.
     */
    @get:JvmSynthetic
    val toVersion: Int

    /**
     * The function that holds the migration process.
     */
    @JvmSynthetic
    fun migrate(settings: Settings)
}