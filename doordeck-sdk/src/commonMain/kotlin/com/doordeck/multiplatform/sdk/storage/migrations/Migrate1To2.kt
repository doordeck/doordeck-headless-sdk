package com.doordeck.multiplatform.sdk.storage.migrations

import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains

object Migrate1To2 : StorageMigration {

    override val fromVersion: Int = 1
    override val toVersion: Int = 2

    private const val KEY_PAIR_VERIFIED_KEY = "KEY_PAIR_VERIFIED_KEY"
    private const val VERIFIED_KEY_PAIR_KEY = "VERIFIED_KEY_PAIR_KEY"
    private const val PUBLIC_KEY_KEY = "PUBLIC_KEY_KEY"

    /**
     * Removes KEY_PAIR_VERIFIED_KEY and adds VERIFIED_KEY_PAIR_KEY with public key as a value
     */
    override fun migrate(settings: Settings) {
        try {
            val keyPairVerified = settings.getBooleanOrNull(KEY_PAIR_VERIFIED_KEY)
            if (keyPairVerified != null) {
                settings.remove(KEY_PAIR_VERIFIED_KEY)
            }
            if (!settings.contains(VERIFIED_KEY_PAIR_KEY) && keyPairVerified == true) {
                val publicKey = settings.getStringOrNull(PUBLIC_KEY_KEY)
                if (publicKey != null) {
                    settings.putString(VERIFIED_KEY_PAIR_KEY, publicKey)
                }
            }
        } catch (exception: Exception) {
            SdkLogger.e("Failed to run migration", exception)
        }
    }
}