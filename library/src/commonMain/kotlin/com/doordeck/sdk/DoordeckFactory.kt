package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment

class DoordeckFactory {
    fun initialize(apiEnvironment: ApiEnvironment, token: String): Doordeck =
        DoordeckImpl(apiEnvironment, token)
}