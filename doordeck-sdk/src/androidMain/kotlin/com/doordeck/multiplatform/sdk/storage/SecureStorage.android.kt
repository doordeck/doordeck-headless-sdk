package com.doordeck.multiplatform.sdk.storage

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.exceptions.MissingAndroidContextException
import com.russhwolf.settings.SharedPreferencesSettings

@JvmSynthetic
internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    val context = applicationContext?.get()
        ?: throw MissingAndroidContextException("Android context is missing")

    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val encryptedPreferences = EncryptedSharedPreferences.create(
        context,
        "${context.packageName}_encrypted_preferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    return DefaultSecureStorage(SharedPreferencesSettings(encryptedPreferences))
}