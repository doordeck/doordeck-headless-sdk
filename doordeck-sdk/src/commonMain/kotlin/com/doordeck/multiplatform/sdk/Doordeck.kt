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
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import kotlin.js.JsExport
import kotlin.native.CName

@JsExport
interface Doordeck {

    @CName("contextManager")
    fun contextManager(): ContextManager

    @JsExport.Ignore
    @CName("accountless")
    fun accountless(): AccountlessResource

    @JsExport.Ignore
    @CName("account")
    fun account(): AccountResource

    @JsExport.Ignore
    @CName("sites")
    fun sites(): SitesResource

    @JsExport.Ignore
    @CName("tiles")
    fun tiles(): TilesResource

    @JsExport.Ignore
    @CName("lockOperations")
    fun lockOperations(): LockOperationsResource

    @JsExport.Ignore
    @CName("platform")
    fun platform(): PlatformResource

    @JsExport.Ignore
    @CName("fusion")
    fun fusion(): FusionResource

    @JsExport.Ignore
    @CName("helper")
    fun helper(): HelperResource

    @JsExport.Ignore
    @CName("crypto")
    fun crypto(): CryptoManager
}