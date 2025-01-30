package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformResourceImplTest : MockTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        PlatformResourceImpl.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress")).await()
    }

    @Test
    fun shouldListApplications() = runTest {
        val response = PlatformResourceImpl.listApplications().await()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldGetApplication() = runTest {
        val response = PlatformResourceImpl.getApplication(DEFAULT_APPLICATION_ID).await()
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        PlatformResourceImpl.updateApplicationName(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        PlatformResourceImpl.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        PlatformResourceImpl.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        PlatformResourceImpl.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        PlatformResourceImpl.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        PlatformResourceImpl.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        PlatformResourceImpl.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences()).await()
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        PlatformResourceImpl.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        PlatformResourceImpl.deleteApplication(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        val response = PlatformResourceImpl.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "").await()
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        PlatformResourceImpl.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = "")).await()
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        PlatformResourceImpl.addAuthIssuer(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        PlatformResourceImpl.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        PlatformResourceImpl.addCorsDomain(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        PlatformResourceImpl.removeCorsDomain(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        PlatformResourceImpl.addApplicationOwner(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        PlatformResourceImpl.removeApplicationOwner(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        val response = PlatformResourceImpl.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID).await()
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }
}