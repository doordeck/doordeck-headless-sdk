package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.data.SiteIdData
import com.doordeck.multiplatform.sdk.model.responses.BasicSiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserForSiteResponse
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SitesApiTest : CallbackTest() {

    @Test
    fun shouldListSites() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val response = callbackApiCall<ResultData<List<BasicSiteResponse>>> {
                SitesApi.listSites(
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            assertTrue { response.success.result.isNotEmpty() }
            assertTrue { response.success.result.any { it.id == PLATFORM_TEST_MAIN_SITE_ID } }
        }
    }

    @Test
    fun shouldGetLocksForSite() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val response = callbackApiCall<ResultData<List<BasicSiteLocksResponse>>> {
                SitesApi.getLocksForSite(
                    data = SiteIdData(PLATFORM_TEST_MAIN_SITE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            assertTrue { response.success.result.isNotEmpty() }
            assertTrue { response.success.result.any { it.id == PLATFORM_TEST_MAIN_LOCK_ID } }
        }
    }

    @Test
    fun shouldGetUsersForSite() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val response = callbackApiCall<ResultData<List<BasicUserForSiteResponse>>> {
                SitesApi.getUsersForSite(
                    data = SiteIdData(PLATFORM_TEST_MAIN_SITE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            assertTrue { response.success.result.isNotEmpty() }
            assertTrue { response.success.result.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }
        }
    }
}