package com.doordeck.multiplatform.sdk.storage.migrations

import kotlin.jvm.JvmSynthetic

/**
 * Simple object that holds the list of migrations.
 */
internal object Migrations {

    /**
     * Ordered storage migrations: the entry at index N upgrades storage from version N to N + 1.
     * Append only. Never reorder or remove entries; replace an obsolete one with a no-op instead.
     */
    private var _migrations: List<StorageMigration> = listOf(
        Migrate0To1,
        Migrate1To2,
        Migrate2To3
    )

    @get:JvmSynthetic
    internal val migrations: List<StorageMigration>
        get() = _migrations

    /**
     * Internal function used in testing to override the migration list.
     */
    @JvmSynthetic
    internal fun overrideMigrations(list: List<StorageMigration>) {
        _migrations = list
    }
}