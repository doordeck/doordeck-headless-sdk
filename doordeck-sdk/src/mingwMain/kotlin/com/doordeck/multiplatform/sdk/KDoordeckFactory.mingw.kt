package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment

actual object KDoordeckFactory {

    @CName("initialize")
    fun initialize(apiEnvironment: ApiEnvironment): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, null, null)

    fun initializeWithAuthToken(apiEnvironment: ApiEnvironment, token: String): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, token, null)

    fun initializeWithAuthAndRefreshTokens(apiEnvironment: ApiEnvironment, token: String, refreshToken: String): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, token, refreshToken)
}