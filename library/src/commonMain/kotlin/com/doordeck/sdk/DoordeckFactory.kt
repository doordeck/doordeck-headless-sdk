package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import com.ionspin.kotlin.crypto.LibsodiumInitializer

class DoordeckFactory {
    fun initialize(apiEnvironment: ApiEnvironment, token: String?, refreshToken: String?): Doordeck = runBlocking {
        LibsodiumInitializer.initialize()
        DoordeckImpl(apiEnvironment, token, refreshToken)
    }
}