package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformResourceImplTest : MockTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        PlatformResourceImpl.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldCreateApplicationAsync() = runTest {
        PlatformResourceImpl.createApplicationAsync(Platform.CreateApplication("name", "companyName", "mailingAddress")).await()
    }

    @Test
    fun shouldListApplications() = runTest {
        val response = PlatformResourceImpl.listApplications()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldListApplicationsAsync() = runTest {
        val response = PlatformResourceImpl.listApplicationsAsync().await()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldGetApplication() = runTest {
        val response = PlatformResourceImpl.getApplication(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldGetApplicationAsync() = runTest {
        val response = PlatformResourceImpl.getApplicationAsync(DEFAULT_APPLICATION_ID).await()
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        PlatformResourceImpl.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationNameAsync() = runTest {
        PlatformResourceImpl.updateApplicationNameAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        PlatformResourceImpl.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyNameAsync() = runTest {
        PlatformResourceImpl.updateApplicationCompanyNameAsync(DEFAULT_APPLICATION_ID, "").await()
    }
    
    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        PlatformResourceImpl.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddressAsync() = runTest {
        PlatformResourceImpl.updateApplicationMailingAddressAsync(DEFAULT_APPLICATION_ID, "").await()
    }
    
    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        PlatformResourceImpl.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicyAsync() = runTest {
        PlatformResourceImpl.updateApplicationPrivacyPolicyAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        PlatformResourceImpl.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContactAsync() = runTest {
        PlatformResourceImpl.updateApplicationSupportContactAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        PlatformResourceImpl.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLinkAsync() = runTest {
        PlatformResourceImpl.updateApplicationAppLinkAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        PlatformResourceImpl.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferencesAsync() = runTest {
        PlatformResourceImpl.updateApplicationEmailPreferencesAsync(DEFAULT_APPLICATION_ID, Platform.EmailPreferences()).await()
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        PlatformResourceImpl.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationLogoUrlAsync() = runTest {
        PlatformResourceImpl.updateApplicationLogoUrlAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        PlatformResourceImpl.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldDeleteApplicationAsync() = runTest {
        PlatformResourceImpl.deleteApplicationAsync(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        val response = PlatformResourceImpl.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldGetLogoUploadUrlAsync() = runTest {
        val response = PlatformResourceImpl.getLogoUploadUrlAsync(DEFAULT_APPLICATION_ID, "").await()
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        PlatformResourceImpl.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthKeyAsync() = runTest {
        PlatformResourceImpl.addAuthKeyAsync(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = "")).await()
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        PlatformResourceImpl.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddAuthIssuerAsync() = runTest {
        PlatformResourceImpl.addAuthIssuerAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        PlatformResourceImpl.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuerAsync() = runTest {
        PlatformResourceImpl.deleteAuthIssuerAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        PlatformResourceImpl.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomainAsync() = runTest {
        PlatformResourceImpl.addCorsDomainAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        PlatformResourceImpl.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomainAsync() = runTest {
        PlatformResourceImpl.removeCorsDomainAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        PlatformResourceImpl.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwnerAsync() = runTest {
        PlatformResourceImpl.addApplicationOwnerAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        PlatformResourceImpl.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwnerAsync() = runTest {
        PlatformResourceImpl.removeApplicationOwnerAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        val response = PlatformResourceImpl.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldGetApplicationOwnersDetailsAsync() = runTest {
        val response = PlatformResourceImpl.getApplicationOwnersDetailsAsync(DEFAULT_APPLICATION_ID).await()
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }
}