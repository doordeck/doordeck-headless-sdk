package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.data.SiteIdData
import com.doordeck.multiplatform.sdk.util.callback
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer

actual object SitesApi {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    @CName("listSites")
    fun listSites(callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                SitesClient.listSitesRequest()
            },
            callback = callback
        )
    }

    /**
     * Get locks for site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-site">API Doc</a>
     */
    @CName("getLocksForSite")
    fun getLocksForSite(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val siteIdData = data.fromJson<SiteIdData>()
                SitesClient.getLocksForSiteRequest(siteIdData.siteId)
            },
            callback = callback
        )
    }

    /**
     * Get users for a site
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-site">API Doc</a>
     */
    @CName("getUsersForSite")
    fun getUsersForSite(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val siteIdData = data.fromJson<SiteIdData>()
                SitesClient.getUsersForSiteRequest(siteIdData.siteId)
            },
            callback = callback
        )
    }
}

actual fun sites(): SitesApi = SitesApi