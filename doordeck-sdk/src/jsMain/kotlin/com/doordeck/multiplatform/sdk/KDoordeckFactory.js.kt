package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.config.toBasicSdkConfig
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.JsExport
import kotlin.js.Promise

@JsExport
object KDoordeckFactory {

    fun initialize(sdkConfig: SdkConfig): Promise<Doordeck> = promise {
        DoordeckFactory.initialize(sdkConfig.toBasicSdkConfig())
    }
}