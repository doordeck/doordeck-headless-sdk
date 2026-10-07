package com.doordeck.multiplatform.sdk

import android.content.Context
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import java.lang.ref.WeakReference

actual val platformType: PlatformType = PlatformType.ANDROID

@JvmSynthetic
internal actual fun getEnvironmentVariable(name: String): String? = try {
    System.getenv(name)
} catch (exception: Throwable) {
    SdkLogger.e(exception) { "Failed to get environment variable: $name" }
    null
}

internal actual object ApplicationContext {
    private var value: WeakReference<Context>? = null

    @JvmSynthetic
    internal fun set(context: Context) {
        value = WeakReference(context)
    }

    @JvmSynthetic
    internal fun get(): Context? {
        return value?.get()
    }
}