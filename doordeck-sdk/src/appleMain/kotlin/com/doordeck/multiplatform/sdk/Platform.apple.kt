package com.doordeck.multiplatform.sdk

actual val platformType by lazy {
    when (Platform.osFamily) {
        OsFamily.MACOSX -> PlatformType.APPLE_MAC
        OsFamily.WATCHOS -> PlatformType.APPLE_WATCH
        OsFamily.IOS -> PlatformType.APPLE_IOS
        else -> PlatformType.APPLE
    }
}

internal actual object ApplicationContext