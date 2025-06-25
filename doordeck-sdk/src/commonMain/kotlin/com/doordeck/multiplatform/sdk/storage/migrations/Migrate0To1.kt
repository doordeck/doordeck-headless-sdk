package com.doordeck.multiplatform.sdk.storage.migrations

import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains

object Migrate0To1 : StorageMigration {

    override val fromVersion: Int = 0
    override val toVersion: Int = 1

    private const val KEY_PAIR_VERIFIED = "KEY_PAIR_VERIFIED"
    private const val KEY_PAIR_VERIFIED_KEY = "KEY_PAIR_VERIFIED_KEY"

    /**
     * Renames KEY_PAIR_VERIFIED value to KEY_PAIR_VERIFIED_KEY
     */
    override fun migrate(settings: Settings) {
        try {
            val keyPairVerified = settings.getBooleanOrNull(KEY_PAIR_VERIFIED)
            if (keyPairVerified != null) {
                settings.remove(KEY_PAIR_VERIFIED)
                if (!settings.contains(KEY_PAIR_VERIFIED_KEY)) {
                    settings.putBoolean(KEY_PAIR_VERIFIED_KEY, keyPairVerified)
                }
            }
        } catch (exception: Exception) {
            SdkLogger.e("Failed to run migration", exception)
        }
    }
}