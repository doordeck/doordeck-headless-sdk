package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.LIST_SITES_RESPONSE
import com.doordeck.multiplatform.sdk.LOCKS_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.USER_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.model.data.SiteIdData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SitesApiTest : CallbackTest() {

    @Test
    fun shouldListSites() = runTest {
        callbackTest(
            apiCall = {
                SitesApi.listSites(staticCFunction(::testCallback))
            },
            expectedResponse = LIST_SITES_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        callbackTest(
            apiCall = {
                SitesApi.getLocksForSite(
                    data = SiteIdData(DEFAULT_SITE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = LOCKS_FOR_SITE_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        callbackTest(
            apiCall = {
                SitesApi.getUsersForSite(
                    data = SiteIdData(DEFAULT_SITE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_FOR_SITE_RESPONSE.toResultDataJson()
        )
    }
}