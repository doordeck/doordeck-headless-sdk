package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.russhwolf.settings.StorageSettings

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage =
    DefaultSecureStorage(StorageSettings())