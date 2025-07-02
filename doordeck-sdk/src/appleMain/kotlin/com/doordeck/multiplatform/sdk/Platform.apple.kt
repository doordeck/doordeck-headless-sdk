package com.doordeck.multiplatform.sdk

actual val platformType by lazy {
    return@lazy when (Platform.osFamily) {
        OsFamily.MACOSX -> PlatformType.APPLE_MAC
        OsFamily.WATCHOS -> PlatformType.APPLE_WATCH
        OsFamily.IOS -> PlatformType.APPLE_PHONE
        else -> PlatformType.UNKNOWN
    }
}

internal actual object ApplicationContext