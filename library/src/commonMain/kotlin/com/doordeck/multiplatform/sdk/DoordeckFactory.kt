package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment

internal object DoordeckFactory {
    fun initialize(applicationContext: ApplicationContext?, apiEnvironment: ApiEnvironment, token: String?, refreshToken: String?): Doordeck {
        return DoordeckImpl(applicationContext, apiEnvironment, token, refreshToken)
    }
}