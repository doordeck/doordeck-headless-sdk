package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.platformType
import com.russhwolf.settings.StorageSettings

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    val settings = if (platformType == PlatformType.JS_BROWSER) StorageSettings() else MemorySettings()
    return DefaultSecureStorage(settings)
}