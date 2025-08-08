package com.doordeck.multiplatform.sdk.model.common

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
enum class ZktecoEntityType {
    DOOR,
    FLOOR
}

@JsExport
@Serializable
enum class ServiceStateType {
    RUNNING,
    STOPPED,
    UNDEFINED
}