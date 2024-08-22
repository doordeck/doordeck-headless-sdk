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
import com.doordeck.multiplatform.sdk.internal.api.LocalUnlockClient
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

class DoordeckImpl(
    apiEnvironment: ApiEnvironment,
    token: String?,
    refreshToken: String?
): Doordeck {

    init {
        // Dependency injection
        startKoin {
            modules(listOf(module {
                val mainContextManager = ContextManagerImpl(token, refreshToken)
                val httpClient = createHttpClient()
                val cloudHttpClient = createCloudHttpClient(apiEnvironment, mainContextManager)
                val fusionHttpClient = createFusionHttpClient(apiEnvironment.fusionHost, mainContextManager)
                single<HttpClient>(named("httpClient")) { httpClient }
                single<HttpClient>(named("cloudHttpClient")) { cloudHttpClient }
                single<HttpClient>(named("fusionHttpClient")) { fusionHttpClient }
                single<ContextManagerImpl> { mainContextManager }
                single<LocalUnlockClient> { LocalUnlockClient(httpClient) }
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
}