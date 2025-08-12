package com.doordeck.multiplatform.sdk.storage.migrations

import kotlin.jvm.JvmSynthetic

/**
 * This value should be updated to match the highest version (toVersion) from the migrations.
 */
@JvmSynthetic
internal const val CURRENT_STORAGE_VERSION = 2

/**
 * Simple object that holds the list of migrations.
 */
internal object Migrations {
    private var _migrations: List<StorageMigration> = listOf(
        Migrate0To1,
        Migrate1To2
    )

    @get:JvmSynthetic
    val migrations: List<StorageMigration>
        get() = _migrations

    /**
     * Internal function used in testing to override the migration list.
     */
    @JvmSynthetic
    fun overrideMigrations(list: List<StorageMigration>) {
        _migrations = list
    }
}