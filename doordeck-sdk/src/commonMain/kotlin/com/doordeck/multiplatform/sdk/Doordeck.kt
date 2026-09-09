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

@JsExport
interface Doordeck {

    fun contextManager(): ContextManager

    fun accountless(): AccountlessApi

    fun account(): AccountApi

    fun sites(): SitesApi

    fun tiles(): TilesApi

    fun lockOperations(): LockOperationsApi

    fun platform(): PlatformApi

    fun fusion(): FusionApi

    fun helper(): HelperApi

    fun crypto(): CryptoManager

    fun release()
}