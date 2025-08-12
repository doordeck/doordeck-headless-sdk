package com.doordeck.multiplatform.sdk.storage.migrations

import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import kotlin.jvm.JvmSynthetic

/**
 * Renames KEY_PAIR_VERIFIED key to KEY_PAIR_VERIFIED_KEY.
 */
internal object Migrate0To1 : StorageMigration {

    @get:JvmSynthetic
    override val fromVersion: Int = 0

    @get:JvmSynthetic
    override val toVersion: Int = 1

    private const val KEY_PAIR_VERIFIED = "KEY_PAIR_VERIFIED"
    private const val KEY_PAIR_VERIFIED_KEY = "KEY_PAIR_VERIFIED_KEY"

    @JvmSynthetic
    override fun migrate(settings: Settings) {
        val keyPairVerified = settings.getBooleanOrNull(KEY_PAIR_VERIFIED)
        if (keyPairVerified != null) {
            settings.remove(KEY_PAIR_VERIFIED)
            if (!settings.contains(KEY_PAIR_VERIFIED_KEY)) {
                settings.putBoolean(KEY_PAIR_VERIFIED_KEY, keyPairVerified)
            }
        }
    }
}