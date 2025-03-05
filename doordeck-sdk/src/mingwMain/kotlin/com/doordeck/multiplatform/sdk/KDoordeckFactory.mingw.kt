package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig

actual object KDoordeckFactory {

    @CName("initialize")
    fun initialize(sdkConfig: SdkConfig): Doordeck {
        return DoordeckFactory.initialize(sdkConfig)
    }
}