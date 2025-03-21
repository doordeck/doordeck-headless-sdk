package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.LIST_SITES_RESPONSE
import com.doordeck.multiplatform.sdk.LOCKS_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_SITE_ID
import com.doordeck.multiplatform.sdk.USER_FOR_SITE_RESPONSE
import com.doordeck.multiplatform.sdk.capturedCallback
import com.doordeck.multiplatform.sdk.model.data.SiteIdData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class SitesApiTest : CallbackTest() {

    @Test
    fun shouldListSites() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            SitesApi.listSites(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(LIST_SITES_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            SitesApi.getLocksForSite(
                data = SiteIdData(DEFAULT_SITE_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(LOCKS_FOR_SITE_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            SitesApi.getUsersForSite(
                data = SiteIdData(DEFAULT_SITE_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(USER_FOR_SITE_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }
}