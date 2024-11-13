package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment

actual object KDoordeckFactory {

    @JvmOverloads
    fun initialize(applicationContext: ApplicationContext, apiEnvironment: ApiEnvironment = ApiEnvironment.PROD,
                   token: String? = null, refreshToken: String? = null): Doordeck =
        DoordeckFactory.initialize(applicationContext, apiEnvironment, token, refreshToken)
}