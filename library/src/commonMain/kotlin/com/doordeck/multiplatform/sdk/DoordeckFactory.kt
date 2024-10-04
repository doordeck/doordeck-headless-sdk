package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.ionspin.kotlin.crypto.LibsodiumInitializer

internal object DoordeckFactory {
    fun initialize(applicationContext: ApplicationContext?, apiEnvironment: ApiEnvironment, token: String?, refreshToken: String?): Doordeck {
        LibsodiumInitializer.initializeWithCallback {  }
        return DoordeckImpl(applicationContext, apiEnvironment, token, refreshToken)
    }
}