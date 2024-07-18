package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.ContextManager
import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.LockOperationsResource
import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.SitesResource
import com.doordeck.multiplatform.sdk.api.TilesResource
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl

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