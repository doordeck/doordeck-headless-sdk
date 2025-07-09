package com.doordeck.multiplatform.sdk

import android.content.Context
import java.lang.ref.WeakReference

actual val platformType: PlatformType = PlatformType.ANDROID

internal actual object ApplicationContext {
    private var value: WeakReference<Context>? = null

    fun set(context: Context) {
        value = WeakReference(context)
    }

    internal fun get(): Context? {
        return value?.get()
    }
}