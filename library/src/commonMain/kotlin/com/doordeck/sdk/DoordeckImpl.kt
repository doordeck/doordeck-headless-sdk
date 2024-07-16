package com.doordeck.sdk

import com.doordeck.sdk.api.AccountResource
import com.doordeck.sdk.api.AccountlessResource
import com.doordeck.sdk.api.ContextManager
import com.doordeck.sdk.api.HelperResource
import com.doordeck.sdk.api.LockOperationsResource
import com.doordeck.sdk.api.PlatformResource
import com.doordeck.sdk.api.SitesResource
import com.doordeck.sdk.api.TilesResource
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.internal.ContextManagerImpl
import com.doordeck.sdk.internal.api.AccountResourceImpl
import com.doordeck.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.sdk.internal.api.HelperResourceImpl
import com.doordeck.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.sdk.internal.api.PlatformResourceImpl
import com.doordeck.sdk.internal.api.SitesResourceImpl
import com.doordeck.sdk.internal.api.TilesResourceImpl

class DoordeckImpl(
    apiEnvironment: ApiEnvironment,
    token: String?,
    refreshToken: String?
): Doordeck {

    private val contextManager = ContextManagerImpl(token, refreshToken)
    private val httpClient = createHttpClient(apiEnvironment, contextManager)

    private val accountless: AccountlessResource = AccountlessResourceImpl(httpClient)
    private val account: AccountResource = AccountResourceImpl(httpClient, contextManager)
    private val sites: SitesResource = SitesResourceImpl(httpClient)
    private val tiles: TilesResource = TilesResourceImpl(httpClient)
    private val lockOperations: LockOperationsResource = LockOperationsResourceImpl(httpClient, contextManager)
    private val platform: PlatformResource = PlatformResourceImpl(httpClient)
    private val helper: HelperResource = HelperResourceImpl(httpClient, platform)

    override fun contextManager(): ContextManager = contextManager
    override fun accountless(): AccountlessResource = accountless
    override fun account(): AccountResource = account
    override fun sites(): SitesResource = sites
    override fun tiles(): TilesResource = tiles
    override fun lockOperations(): LockOperationsResource = lockOperations
    override fun platform(): PlatformResource = platform
    override fun helper(): HelperResource = helper
}