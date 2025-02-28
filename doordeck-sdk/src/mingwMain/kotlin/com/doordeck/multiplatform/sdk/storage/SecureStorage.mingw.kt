package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    return DisabledSecureStorage() // Disabled by default
}