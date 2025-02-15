package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.model.common.ApiEnvironment

actual object KDoordeckFactory {

    fun initialize(apiEnvironment: ApiEnvironment): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, null, null)

    fun initialize(apiEnvironment: ApiEnvironment, token: String): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, token, null)

    fun initialize(apiEnvironment: ApiEnvironment, token: String, refreshToken: String): Doordeck =
        DoordeckFactory.initialize(null, apiEnvironment, token, refreshToken)
}