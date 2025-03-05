package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig

@JsExport
actual object KDoordeckFactory {

    fun initialize(sdkConfig: SdkConfig): Doordeck {
        return DoordeckFactory.initialize(sdkConfig)
    }
}