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

    @JsExport.Ignore
    fun accountless(): AccountlessResource
    @JsExport.Ignore
    fun account(): AccountResource
    @JsExport.Ignore
    fun sites(): SitesResource
    @JsExport.Ignore
    fun tiles(): TilesResource
    @JsExport.Ignore
    fun lockOperations(): LockOperationsResource
    @JsExport.Ignore
    fun platform(): PlatformResource
    @JsExport.Ignore
    fun fusion(): FusionResource
    @JsExport.Ignore
    fun helper(): HelperResource
}