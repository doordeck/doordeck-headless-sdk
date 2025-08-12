package com.doordeck.multiplatform.sdk.storage.migrations

import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import kotlin.jvm.JvmSynthetic

/**
 * Removes KEY_PAIR_VERIFIED_KEY and adds VERIFIED_KEY_PAIR_KEY with the public key as its value.
 */
internal object Migrate1To2 : StorageMigration {

    @get:JvmSynthetic
    override val fromVersion: Int = 1

    @get:JvmSynthetic
    override val toVersion: Int = 2

    private const val KEY_PAIR_VERIFIED_KEY = "KEY_PAIR_VERIFIED_KEY"
    private const val VERIFIED_KEY_PAIR_KEY = "VERIFIED_KEY_PAIR_KEY"
    private const val PUBLIC_KEY_KEY = "PUBLIC_KEY_KEY"

    @JvmSynthetic
    override fun migrate(settings: Settings) {
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
    }
}