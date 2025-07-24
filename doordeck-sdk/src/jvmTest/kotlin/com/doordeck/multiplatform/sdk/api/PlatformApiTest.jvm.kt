package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.model.data.Platform
import com.doordeck.multiplatform.sdk.randomUUID
import com.doordeck.multiplatform.sdk.util.toUUID
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformApiTest : MockTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        PlatformApi.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldCreateApplicationAsync() = runTest {
        PlatformApi.createApplicationAsync(Platform.CreateApplication("name", "companyName", "mailingAddress")).await()
    }

    @Test
    fun shouldListApplications() = runTest {
        val response = PlatformApi.listApplications()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldListApplicationsAsync() = runTest {
        val response = PlatformApi.listApplicationsAsync().await()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldGetApplication() = runTest {
        val response = PlatformApi.getApplication(DEFAULT_APPLICATION_ID.toUUID())
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldGetApplicationAsync() = runTest {
        val response = PlatformApi.getApplicationAsync(DEFAULT_APPLICATION_ID.toUUID()).await()
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        PlatformApi.updateApplicationName(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateApplicationNameAsync() = runTest {
        PlatformApi.updateApplicationNameAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        PlatformApi.updateApplicationCompanyName(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateApplicationCompanyNameAsync() = runTest {
        PlatformApi.updateApplicationCompanyNameAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }
    
    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        PlatformApi.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddressAsync() = runTest {
        PlatformApi.updateApplicationMailingAddressAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }
    
    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        PlatformApi.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicyAsync() = runTest {
        PlatformApi.updateApplicationPrivacyPolicyAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        PlatformApi.updateApplicationSupportContact(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateApplicationSupportContactAsync() = runTest {
        PlatformApi.updateApplicationSupportContactAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        PlatformApi.updateApplicationAppLink(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateApplicationAppLinkAsync() = runTest {
        PlatformApi.updateApplicationAppLinkAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        PlatformApi.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID.toUUID(), Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferencesAsync() = runTest {
        PlatformApi.updateApplicationEmailPreferencesAsync(DEFAULT_APPLICATION_ID.toUUID(), Platform.EmailPreferences()).await()
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        PlatformApi.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateApplicationLogoUrlAsync() = runTest {
        PlatformApi.updateApplicationLogoUrlAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        PlatformApi.deleteApplication(DEFAULT_APPLICATION_ID.toUUID())
    }

    @Test
    fun shouldDeleteApplicationAsync() = runTest {
        PlatformApi.deleteApplicationAsync(DEFAULT_APPLICATION_ID.toUUID()).await()
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        val response = PlatformApi.getLogoUploadUrl(DEFAULT_APPLICATION_ID.toUUID(), "")
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldGetLogoUploadUrlAsync() = runTest {
        val response = PlatformApi.getLogoUploadUrlAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        PlatformApi.addAuthKey(DEFAULT_APPLICATION_ID.toUUID(), Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthKeyAsync() = runTest {
        PlatformApi.addAuthKeyAsync(DEFAULT_APPLICATION_ID.toUUID(), Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = "")).await()
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        PlatformApi.addAuthIssuer(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldAddAuthIssuerAsync() = runTest {
        PlatformApi.addAuthIssuerAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        PlatformApi.deleteAuthIssuer(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldDeleteAuthIssuerAsync() = runTest {
        PlatformApi.deleteAuthIssuerAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        PlatformApi.addCorsDomain(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldAddCorsDomainAsync() = runTest {
        PlatformApi.addCorsDomainAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        PlatformApi.removeCorsDomain(DEFAULT_APPLICATION_ID.toUUID(), "")
    }

    @Test
    fun shouldRemoveCorsDomainAsync() = runTest {
        PlatformApi.removeCorsDomainAsync(DEFAULT_APPLICATION_ID.toUUID(), "").await()
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        PlatformApi.addApplicationOwner(DEFAULT_APPLICATION_ID.toUUID(), randomUUID())
    }

    @Test
    fun shouldAddApplicationOwnerAsync() = runTest {
        PlatformApi.addApplicationOwnerAsync(DEFAULT_APPLICATION_ID.toUUID(), randomUUID()).await()
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        PlatformApi.removeApplicationOwner(DEFAULT_APPLICATION_ID.toUUID(), randomUUID())
    }

    @Test
    fun shouldRemoveApplicationOwnerAsync() = runTest {
        PlatformApi.removeApplicationOwnerAsync(DEFAULT_APPLICATION_ID.toUUID(), randomUUID()).await()
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        val response = PlatformApi.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID.toUUID())
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldGetApplicationOwnersDetailsAsync() = runTest {
        val response = PlatformApi.getApplicationOwnersDetailsAsync(DEFAULT_APPLICATION_ID.toUUID()).await()
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }
}