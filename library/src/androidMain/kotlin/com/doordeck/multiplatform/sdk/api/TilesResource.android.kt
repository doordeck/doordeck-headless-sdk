package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.internal.api.SiteAdmin
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface TilesResource {
    /**
     * Get locks belonging to tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-belonging-to-tile-v3">API Doc</a>
     */
    suspend fun getLocksBelongingToTile(tileId: String): TileLocksResponse

    fun getLocksBelongingToTileAsync(tileId: String): CompletableFuture<TileLocksResponse>

    /**
     * Associate multiple locks (devices) to a single tile
     *
     * @see <a href="https://developer.doordeck.com/docs/#associate-multiple-locks-devices-to-a-single-tile">API Doc</a>
     */
    @SiteAdmin
    suspend fun associateMultipleLocks(tileId: String, siteId: String, lockIds: List<String>)

    @SiteAdmin
    fun associateMultipleLocksAsync(tileId: String, siteId: String, lockIds: List<String>): CompletableFuture<Unit>
}

actual fun tiles(): TilesResource = TilesResourceImpl(getKoin().get<HttpClient>(named("cloudHttpClient")))