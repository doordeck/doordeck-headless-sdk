package com.doordeck.multiplatform.sdk.storage.migrations

import com.russhwolf.settings.Settings

interface StorageMigration {
    val fromVersion: Int
    val toVersion: Int
    fun migrate(settings: Settings)
}