package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformResourceImplTest : MockTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        PlatformResourceImpl.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldListApplications() = runTest {
        val response = PlatformResourceImpl.listApplications()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldGetApplication() = runTest {
        val response = PlatformResourceImpl.getApplication(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        PlatformResourceImpl.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        PlatformResourceImpl.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        PlatformResourceImpl.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        PlatformResourceImpl.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        PlatformResourceImpl.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        PlatformResourceImpl.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        PlatformResourceImpl.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        PlatformResourceImpl.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        PlatformResourceImpl.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        val response = PlatformResourceImpl.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        PlatformResourceImpl.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        PlatformResourceImpl.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        PlatformResourceImpl.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        PlatformResourceImpl.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        PlatformResourceImpl.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        PlatformResourceImpl.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        PlatformResourceImpl.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        val response = PlatformResourceImpl.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }
}