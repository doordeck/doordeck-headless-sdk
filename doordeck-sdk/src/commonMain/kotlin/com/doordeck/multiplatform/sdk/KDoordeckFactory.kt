package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig
import kotlin.js.JsExport
import kotlin.native.CName

@JsExport
object KDoordeckFactory {

    @CName("initialize")
    fun initialize(sdkConfig: SdkConfig): Doordeck {
        return DoordeckFactory.initialize(sdkConfig)
    }
}