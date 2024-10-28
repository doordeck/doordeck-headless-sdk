package com.doordeck.multiplatform.sdk.util

import kotlinx.cinterop.usePinned
import platform.Foundation.NSData

fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), bytes, length)
    }
}