package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.internal.api.AccountResourceImpl
import com.doordeck.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.sdk.internal.api.PlatformResourceImpl
import com.doordeck.sdk.internal.api.SitesResourceImpl
import com.doordeck.sdk.internal.api.TilesResourceImpl

class DoordeckImpl(
    override val apiEnvironment: ApiEnvironment,
    override val token: String
): Doordeck {

    private val httpClient = createHttpClient(apiEnvironment, token)

    private val account = AccountResourceImpl(httpClient)
    private val sites = SitesResourceImpl(httpClient)
    private val tiles = TilesResourceImpl(httpClient)
    private val lockOperations = LockOperationsResourceImpl(httpClient)
    private val platform = PlatformResourceImpl(httpClient)

    override fun account() = account
    override fun sites() = sites
    override fun tiles() = tiles
    override fun lockOperations() = lockOperations
    override fun platform() = platform
}