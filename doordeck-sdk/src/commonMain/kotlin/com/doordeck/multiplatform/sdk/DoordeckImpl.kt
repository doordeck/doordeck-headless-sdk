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

internal object DoordeckImpl : Doordeck {

    /**
     * APIs
     */
    private val accountless: AccountlessApi = com.doordeck.multiplatform.sdk.api.accountless()
    private val account: AccountApi = com.doordeck.multiplatform.sdk.api.account()
    private val sites: SitesApi = com.doordeck.multiplatform.sdk.api.sites()
    private val tiles: TilesApi = com.doordeck.multiplatform.sdk.api.tiles()
    private val lockOperations: LockOperationsApi = com.doordeck.multiplatform.sdk.api.lockOperations()
    private val platform: PlatformApi = com.doordeck.multiplatform.sdk.api.platform()
    private val fusion: FusionApi = com.doordeck.multiplatform.sdk.api.fusion()
    private val helper: HelperApi = com.doordeck.multiplatform.sdk.api.helper()
    private val contextManager: ContextManager = com.doordeck.multiplatform.sdk.context.contextManager()

    override fun contextManager(): ContextManager = contextManager
    override fun accountless(): AccountlessApi = accountless
    override fun account(): AccountApi = account
    override fun sites(): SitesApi = sites
    override fun tiles(): TilesApi = tiles
    override fun lockOperations(): LockOperationsApi = lockOperations
    override fun platform(): PlatformApi = platform
    override fun fusion(): FusionApi = fusion
    override fun helper(): HelperApi = helper
    override fun crypto(): CryptoManager = CryptoManager
}