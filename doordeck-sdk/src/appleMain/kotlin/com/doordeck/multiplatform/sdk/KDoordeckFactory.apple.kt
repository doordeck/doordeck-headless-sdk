package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig

object KDoordeckFactory {

    suspend fun initialize(sdkConfig: SdkConfig): Doordeck =
        DoordeckFactory.initialize(sdkConfig)
}