package com.doordeck.multiplatform.sdk.model.values

actual typealias DurationValue = Double

internal actual fun Double.toDurationValue(): DurationValue {
    return this
}

internal actual fun DurationValue.toDurationValueDouble(): Double {
    return this
}