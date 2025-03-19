package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.SitesClient
import com.doordeck.multiplatform.sdk.model.data.GetLocksForSiteData
import com.doordeck.multiplatform.sdk.model.data.GetUsersForSiteData
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.launchCallback
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef

actual object SitesApi {
    /**
     * List sites
     *
     * @see <a href="https://developer.doordeck.com/docs/#sites">API Doc</a>
     */
    @CName("listSites")
    fun listSites(callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
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
    fun getLocksForSite(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val getLocksForSiteData = data.fromJson<GetLocksForSiteData>()
                SitesClient.getLocksForSiteRequest(getLocksForSiteData.siteId)
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
    fun getUsersForSite(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val getUsersForSiteData = data.fromJson<GetUsersForSiteData>()
                SitesClient.getUsersForSiteRequest(getUsersForSiteData.siteId)
            },
            callback = callback
        )
    }
}

actual fun sites(): SitesApi = SitesApi