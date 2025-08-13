package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext

@JvmSynthetic
internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage =
    DefaultSecureStorage(MemorySettings())