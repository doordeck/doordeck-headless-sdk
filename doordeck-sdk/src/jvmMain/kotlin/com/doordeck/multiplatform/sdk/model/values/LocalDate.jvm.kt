package com.doordeck.multiplatform.sdk.model.values

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
private val localDateFormat = LocalDate.Format {
    byUnicodePattern("yyyy-MM-dd")
}

actual typealias LocalDateValue = LocalDate

internal actual fun String.toLocalDateValue(): LocalDateValue {
    return LocalDate.parse(this, localDateFormat)
}

internal actual fun LocalDateValue.toLocalDateValueString(): String {
    return localDateFormat.format(this)
}