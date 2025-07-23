package com.doordeck.multiplatform.sdk.model.values

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
private val localTimeFormat = LocalTime.Format {
    byUnicodePattern("HH:mm")
}

actual typealias LocalTimeValue = LocalTime

internal actual fun String.toLocalTimeValue(): LocalTimeValue {
    return LocalTime.parse(this,  localTimeFormat)
}

internal actual fun LocalTimeValue.toLocalTimeValueString(): String {
    return localTimeFormat.format(this)
}