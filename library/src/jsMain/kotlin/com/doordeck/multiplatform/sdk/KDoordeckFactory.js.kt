package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment

@JsExport
actual object KDoordeckFactory {

    @JsName("initialize")
    fun initialize(apiEnvironment: ApiEnvironment): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, null, null)

    @JsName("initializeWithAuthToken")
    fun initialize(apiEnvironment: ApiEnvironment, token: String): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, token, null)

    @JsName("initializeWithAuthAndRefreshTokens")
    fun initialize(apiEnvironment: ApiEnvironment, token: String, refreshToken: String): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, token, refreshToken)
}