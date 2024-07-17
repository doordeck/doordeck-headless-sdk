package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
class KDoordeckFactory {

    @JsName("initialize")
    fun initialize(apiEnvironment: ApiEnvironment): Doordeck =
        DoordeckFactory().initialize(apiEnvironment, null, null)

    @JsName("initializeWithAuthToken")
    fun initialize(apiEnvironment: ApiEnvironment, token: String): Doordeck =
        DoordeckFactory().initialize(apiEnvironment, token, null)


    @JsName("initializeWithAuthAndRefreshTokens")
    fun initialize(apiEnvironment: ApiEnvironment, token: String, refreshToken: String): Doordeck =
        DoordeckFactory().initialize(apiEnvironment, token, refreshToken)
}