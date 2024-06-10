package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.TilesResource
import com.doordeck.sdk.api.requests.AssociateMultipleLocksRequest
import com.doordeck.sdk.api.responses.TileLocksResponse
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class TilesResourceImpl(
    private val httpClient: HttpClient
) : TilesResource {

    override fun getLocksBelongingToTile(tileId: String): TileLocksResponse = runBlocking {
        httpClient.get(Paths.getLocksBelongingToTilePath(tileId)).body()
    }

    // TODO we should have some annotations to indicate the permission level needed for some API calls?
    //  e.g. this would be @LockAdmin or @SiteAdmin? i dunno, some nice way to communicate to the user what level of permissions they need
    override fun associateTileWithLock(tileId: String, lockId: String) {
        runBlocking {
            httpClient.put(Paths.getAssociateTileWithLockPath(tileId, lockId))
        }
    }

    override fun disassociateTileFromLock(tileId: String, lockId: String) {
        runBlocking {
            httpClient.delete(Paths.getDisassociateTileFromLockPath(tileId, lockId))
        }
    }

    override fun associateMultipleLocks(tileId: String, siteId: String, lockIds: Array<String>) {
        runBlocking {
            httpClient.put(Paths.getAssociateMultipleLocksToASingleTilePath(tileId)) {
                addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
                setBody(AssociateMultipleLocksRequest(siteId, lockIds))
            }
        }
    }
}