package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.model.data.Platform
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformApiTest : MockTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        PlatformApi.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress")).await()
    }

    @Test
    fun shouldListApplications() = runTest {
        val response = PlatformApi.listApplications().await()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldGetApplication() = runTest {
        val response = PlatformApi.getApplication(DEFAULT_APPLICATION_ID).await()
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        PlatformApi.updateApplicationName(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        PlatformApi.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        PlatformApi.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        PlatformApi.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        PlatformApi.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        PlatformApi.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        PlatformApi.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences()).await()
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        PlatformApi.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        PlatformApi.deleteApplication(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        val response = PlatformApi.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "").await()
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        PlatformApi.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = "")).await()
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        PlatformApi.addAuthIssuer(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        PlatformApi.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        PlatformApi.addCorsDomain(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        PlatformApi.removeCorsDomain(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        PlatformApi.addApplicationOwner(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        PlatformApi.removeApplicationOwner(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        val response = PlatformApi.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID).await()
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }
}