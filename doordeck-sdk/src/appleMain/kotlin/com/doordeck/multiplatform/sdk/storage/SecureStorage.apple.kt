package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.russhwolf.settings.KeychainSettings

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    return DefaultSecureStorage(KeychainSettings("doordeck-sdk"))
}