package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment

actual object KDoordeckFactory {

    fun initialize(applicationContext: ApplicationContext, apiEnvironment: ApiEnvironment): Doordeck =
        DoordeckFactory.initialize(applicationContext, apiEnvironment, null, null)

    fun initialize(applicationContext: ApplicationContext, apiEnvironment: ApiEnvironment, token: String): Doordeck =
        DoordeckFactory.initialize(applicationContext, apiEnvironment, token, null)

    fun initialize(applicationContext: ApplicationContext, apiEnvironment: ApiEnvironment, token: String, refreshToken: String): Doordeck =
        DoordeckFactory.initialize(applicationContext, apiEnvironment, token, refreshToken)
}