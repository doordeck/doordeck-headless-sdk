package com.doordeck.multiplatform.sdk.model.values

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
private val localDateFormat = LocalDate.Format {
    byUnicodePattern("yyyy-MM-dd")
}

actual typealias PlatformLocalDate = LocalDate

internal actual fun String.toPlatformLocalDate(): PlatformLocalDate {
    return LocalDate.parse(this, localDateFormat)
}

internal actual fun PlatformLocalDate.toPlatformLocalDateString(): String {
    return localDateFormat.format(this)
}