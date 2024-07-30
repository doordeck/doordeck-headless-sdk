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
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
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

    /**
     * Http clients
     */
    private val httpClient = createHttpClient()
    private val fusionHttpClient = createFusionHttpClient(apiEnvironment.fusionHost, contextManager)
    private val cloudHttpClient = createCloudHttpClient(apiEnvironment, contextManager)

    /**
     * Resources
     */
    private val accountless: AccountlessResource = AccountlessResourceImpl(cloudHttpClient)
    private val account: AccountResource = AccountResourceImpl(cloudHttpClient, contextManager)
    private val sites: SitesResource = SitesResourceImpl(cloudHttpClient)
    private val tiles: TilesResource = TilesResourceImpl(cloudHttpClient)
    private val lockOperations: LockOperationsResource = LockOperationsResourceImpl(cloudHttpClient, contextManager)
    private val platform: PlatformResource = PlatformResourceImpl(cloudHttpClient)
    private val helper: HelperResource = HelperResourceImpl(httpClient, platform)
    private val fusion: FusionResource = FusionResourceImpl(fusionHttpClient)

    override fun contextManager(): ContextManager = contextManager
    override fun accountless(): AccountlessResource = accountless
    override fun account(): AccountResource = account
    override fun sites(): SitesResource = sites
    override fun tiles(): TilesResource = tiles
    override fun lockOperations(): LockOperationsResource = lockOperations
    override fun platform(): PlatformResource = platform
    override fun helper(): HelperResource = helper
    override fun fusion(): FusionResource = fusion
}