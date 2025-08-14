package com.doordeck.multiplatform.sdk

import android.content.Context
import java.lang.ref.WeakReference

actual val platformType: PlatformType = PlatformType.ANDROID

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