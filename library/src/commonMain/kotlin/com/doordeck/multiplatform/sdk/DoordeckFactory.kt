package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.ionspin.kotlin.crypto.LibsodiumInitializer

object DoordeckFactory {
    fun initialize(apiEnvironment: ApiEnvironment, token: String?, refreshToken: String?): Doordeck {
        LibsodiumInitializer.initializeWithCallback {  }
        return DoordeckImpl(apiEnvironment, token, refreshToken)
    }
}