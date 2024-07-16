package com.doordeck.sdk

import com.doordeck.sdk.api.AccountResource
import com.doordeck.sdk.api.AccountlessResource
import com.doordeck.sdk.api.ContextManager
import com.doordeck.sdk.api.HelperResource
import com.doordeck.sdk.api.LockOperationsResource
import com.doordeck.sdk.api.PlatformResource
import com.doordeck.sdk.api.SitesResource
import com.doordeck.sdk.api.TilesResource
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
}