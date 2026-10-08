package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.russhwolf.settings.KeychainSettings

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage =
    CachedStorage(DefaultSecureStorage(KeychainSettings("doordeck-sdk")))