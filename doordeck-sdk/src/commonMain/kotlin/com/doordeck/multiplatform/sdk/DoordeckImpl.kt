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
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.FusionClient
import com.doordeck.multiplatform.sdk.internal.api.HelperClient
import com.doordeck.multiplatform.sdk.internal.api.LocalUnlockClient
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsClient
import com.doordeck.multiplatform.sdk.internal.api.PlatformClient
import com.doordeck.multiplatform.sdk.internal.api.SitesClient
import com.doordeck.multiplatform.sdk.internal.api.TilesClient
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

internal class DoordeckImpl(
    applicationContext: ApplicationContext?,
    apiEnvironment: ApiEnvironment,
    token: String?,
    refreshToken: String?
): Doordeck {

    init {
        // Dependency injection
        startKoin {
            modules(listOf(module {
                val mainContextManager = ContextManagerImpl(applicationContext, token, refreshToken)
                val httpClient = createHttpClient()
                val cloudHttpClient = createCloudHttpClient(apiEnvironment, mainContextManager)
                val fusionHttpClient = createFusionHttpClient(apiEnvironment.fusionHost, mainContextManager)
                single<AccountClient> { AccountClient(cloudHttpClient, mainContextManager) }
                single<AccountlessClient> { AccountlessClient(cloudHttpClient, mainContextManager) }
                single<FusionClient> { FusionClient(fusionHttpClient, mainContextManager) }
                single<LocalUnlockClient> { LocalUnlockClient(httpClient) }
                single<LockOperationsClient> {
                    LockOperationsClient(cloudHttpClient, mainContextManager, get())
                }
                single<PlatformClient> { PlatformClient(cloudHttpClient) }
                single<SitesClient> { SitesClient(cloudHttpClient) }
                single<TilesClient> { TilesClient(cloudHttpClient) }
                single<HelperClient> { HelperClient(httpClient, get(), get(), get(), mainContextManager) }
                single<ContextManagerImpl> { mainContextManager }
            }))
        }
    }

    /**
     * Resources
     */
    private val accountless: AccountlessResource = com.doordeck.multiplatform.sdk.api.accountless()
    private val account: AccountResource = com.doordeck.multiplatform.sdk.api.account()
    private val sites: SitesResource = com.doordeck.multiplatform.sdk.api.sites()
    private val tiles: TilesResource = com.doordeck.multiplatform.sdk.api.tiles()
    private val lockOperations: LockOperationsResource = com.doordeck.multiplatform.sdk.api.lockOperations()
    private val platform: PlatformResource = com.doordeck.multiplatform.sdk.api.platform()
    private val fusion: FusionResource = com.doordeck.multiplatform.sdk.api.fusion()
    private val helper: HelperResource = com.doordeck.multiplatform.sdk.api.helper()

    override fun contextManager(): ContextManager = getKoin().get<ContextManagerImpl>()
    override fun accountless(): AccountlessResource = accountless
    override fun account(): AccountResource = account
    override fun sites(): SitesResource = sites
    override fun tiles(): TilesResource = tiles
    override fun lockOperations(): LockOperationsResource = lockOperations
    override fun platform(): PlatformResource = platform
    override fun fusion(): FusionResource = fusion
    override fun helper(): HelperResource = helper
    override fun crypto(): CryptoManager = CryptoManager
}