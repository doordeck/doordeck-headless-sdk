package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.model.data.BasicPlatform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformApiTest : MockTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        PlatformApi.createApplication(BasicPlatform.BasicCreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldListApplications() = runTest {
        val response = PlatformApi.listApplications()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldGetApplication() = runTest {
        val response = PlatformApi.getApplication(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        PlatformApi.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        PlatformApi.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        PlatformApi.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        PlatformApi.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        PlatformApi.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        PlatformApi.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        PlatformApi.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, BasicPlatform.BasicEmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        PlatformApi.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        PlatformApi.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        val response = PlatformApi.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        PlatformApi.addAuthKey(DEFAULT_APPLICATION_ID, BasicPlatform.BasicEcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        PlatformApi.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        PlatformApi.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        PlatformApi.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        PlatformApi.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        PlatformApi.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        PlatformApi.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        val response = PlatformApi.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }
}