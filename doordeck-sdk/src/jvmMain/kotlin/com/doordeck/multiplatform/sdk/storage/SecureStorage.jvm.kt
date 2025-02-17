package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.russhwolf.settings.PropertiesSettings
import java.util.Properties

actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    return DefaultSecureStorage(PropertiesSettings(Properties()))
}