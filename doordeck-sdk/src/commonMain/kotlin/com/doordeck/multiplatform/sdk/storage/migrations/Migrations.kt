package com.doordeck.multiplatform.sdk.storage.migrations

/**
 * This value should be updated to match the highest version (toVersion) from the migrations.
 */
internal const val CURRENT_STORAGE_VERSION = 1

/**
 * Simple object that holds the list of migrations.
 */
internal object Migrations {
    val migrations = listOf(
        Migrate0To1
    )
}