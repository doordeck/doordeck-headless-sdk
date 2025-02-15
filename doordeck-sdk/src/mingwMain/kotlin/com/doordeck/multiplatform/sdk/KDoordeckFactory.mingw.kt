package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.model.common.ApiEnvironment

actual object KDoordeckFactory {

    @CName("initialize")
    fun initialize(apiEnvironment: ApiEnvironment): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, null, null)

    @CName("initializeWithAuthToken")
    fun initializeWithAuthToken(apiEnvironment: ApiEnvironment, token: String): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, token, null)

    @CName("initializeWithAuthAndRefreshTokens")
    fun initializeWithAuthAndRefreshTokens(apiEnvironment: ApiEnvironment, token: String, refreshToken: String): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, token, refreshToken)
}