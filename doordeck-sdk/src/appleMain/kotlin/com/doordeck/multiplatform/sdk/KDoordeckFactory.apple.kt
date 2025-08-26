package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.config.toBasicSdkConfig

object KDoordeckFactory {

    suspend fun initialize(sdkConfig: SdkConfig): Doordeck =
        DoordeckFactory.initialize(sdkConfig.toBasicSdkConfig())
}