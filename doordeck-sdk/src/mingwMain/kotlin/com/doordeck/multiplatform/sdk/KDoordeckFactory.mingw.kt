package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.config.toBasicSdkConfig
import kotlinx.coroutines.runBlocking

object KDoordeckFactory {

    @CName("initialize")
    fun initialize(sdkConfig: SdkConfig): Doordeck = runBlocking {
        DoordeckFactory.initialize(sdkConfig.toBasicSdkConfig())
    }
}