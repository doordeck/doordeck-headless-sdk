package com.doordeck.multiplatform.sdk.model.values

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
private val localTimeFormat = LocalTime.Format {
    byUnicodePattern("HH:mm")
}

actual typealias PlatformLocalTime = LocalTime

internal actual fun String.toPlatformLocalTime(): PlatformLocalTime {
    return LocalTime.parse(this,  localTimeFormat)
}

internal actual fun PlatformLocalTime.toPlatformLocalTimeString(): String {
    return localTimeFormat.format(this)
}