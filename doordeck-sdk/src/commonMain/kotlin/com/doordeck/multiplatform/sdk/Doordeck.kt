package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.AccountApi
import com.doordeck.multiplatform.sdk.api.AccountlessApi
import com.doordeck.multiplatform.sdk.api.FusionApi
import com.doordeck.multiplatform.sdk.api.HelperApi
import com.doordeck.multiplatform.sdk.api.LockOperationsApi
import com.doordeck.multiplatform.sdk.api.PlatformApi
import com.doordeck.multiplatform.sdk.api.SitesApi
import com.doordeck.multiplatform.sdk.api.TilesApi
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import kotlin.js.JsExport
import kotlin.native.CName

@JsExport
interface Doordeck {

    @CName("contextManager")
    fun contextManager(): ContextManager

    @JsExport.Ignore
    @CName("accountless")
    fun accountless(): AccountlessApi

    @JsExport.Ignore
    @CName("account")
    fun account(): AccountApi

    @JsExport.Ignore
    @CName("sites")
    fun sites(): SitesApi

    @JsExport.Ignore
    @CName("tiles")
    fun tiles(): TilesApi

    @JsExport.Ignore
    @CName("lockOperations")
    fun lockOperations(): LockOperationsApi

    @JsExport.Ignore
    @CName("platform")
    fun platform(): PlatformApi

    @JsExport.Ignore
    @CName("fusion")
    fun fusion(): FusionApi

    @JsExport.Ignore
    @CName("helper")
    fun helper(): HelperApi

    @JsExport.Ignore
    @CName("crypto")
    fun crypto(): CryptoManager
}