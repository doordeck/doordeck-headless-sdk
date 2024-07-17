package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
enum class UserRole {
    ADMIN,
    USER
}