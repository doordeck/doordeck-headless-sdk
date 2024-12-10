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
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl

internal object DoordeckImpl : Doordeck {

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

    override fun contextManager(): ContextManager = ContextManagerImpl
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