package com.doordeck.multiplatform.sdk.storage.migrations

import com.russhwolf.settings.Settings

/**
 * Removes the stored value from API_ENVIRONMENT_KEY and FUSION_HOST_KEY from the storage.
 */
internal object Migrate3To4 : StorageMigration {

    override val fromVersion: Int = 3
    override val toVersion: Int = 4

    private const val API_ENVIRONMENT_KEY = "API_ENVIRONMENT_KEY"
    private const val FUSION_HOST_KEY = "FUSION_HOST_KEY"

    override fun migrate(settings: Settings) {
        val apiEnvironmentKey = settings.getStringOrNull(API_ENVIRONMENT_KEY)
        if (apiEnvironmentKey != null) {
            settings.remove(API_ENVIRONMENT_KEY)
        }
        val fusionHostKey = settings.getStringOrNull(FUSION_HOST_KEY)
        if (fusionHostKey != null) {
            settings.remove(FUSION_HOST_KEY)
        }
    }
}