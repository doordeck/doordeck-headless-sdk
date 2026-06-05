package com.doordeck.multiplatform.sdk.storage.migrations

import com.russhwolf.settings.Settings

/**
 * Removes the stored value from FUSION_HOST_KEY when it's set to 'null'
 */
internal object Migrate2To3 : StorageMigration {

    override val fromVersion: Int = 2
    override val toVersion: Int = 3

    private const val FUSION_HOST_KEY = "FUSION_HOST_KEY"

    override fun migrate(settings: Settings) {
        val fusionHostKey = settings.getStringOrNull(FUSION_HOST_KEY)
        if (fusionHostKey != null && fusionHostKey == "null") {
            settings.remove(FUSION_HOST_KEY)
        }
    }
}