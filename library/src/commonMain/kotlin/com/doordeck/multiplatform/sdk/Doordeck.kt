package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.ContextManager
import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.LockOperationsResource
import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.TilesResource
import kotlin.js.JsExport

@JsExport
interface Doordeck {

    fun contextManager(): ContextManager
    fun accountless(): AccountlessResource
    fun account(): AccountResource
    fun sites(): SitesResource
    fun tiles(): TilesResource
    fun lockOperations(): LockOperationsResource
    fun platform(): PlatformResource
    fun helper(): HelperResource
    fun fusion(): FusionResource
}