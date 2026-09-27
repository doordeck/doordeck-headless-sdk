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

    @CName("accountless")
    fun accountless(): AccountlessApi

    @CName("account")
    fun account(): AccountApi

    @CName("sites")
    fun sites(): SitesApi

    @CName("tiles")
    fun tiles(): TilesApi

    @CName("lockOperations")
    fun lockOperations(): LockOperationsApi

    @CName("platform")
    fun platform(): PlatformApi

    @CName("fusion")
    fun fusion(): FusionApi

    @CName("helper")
    fun helper(): HelperApi

    @CName("crypto")
    fun crypto(): CryptoManager

    @CName("release")
    fun release()
}