package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

object KDoordeckFactory {

    suspend fun initialize(sdkConfig: SdkConfig): Doordeck =
        DoordeckFactory.initialize(sdkConfig)

    fun initializeAsync(sdkConfig: SdkConfig): CompletableFuture<Doordeck> = completableFuture {
        initialize(sdkConfig)
    }
}