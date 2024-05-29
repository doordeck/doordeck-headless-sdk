package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
class KDoordeckFactory {
    @JsName("initialize")
    fun initialize(apiEnvironment: ApiEnvironment, token: String): Doordeck =
        DoordeckFactory().initialize(apiEnvironment, token)
}