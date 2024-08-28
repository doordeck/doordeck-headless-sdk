package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PlatformResourceImplTest {

    private val platform = PlatformResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldCreateApplication() = runTest {
        platform.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldCreateApplicationAsync() = runTest {
        platform.createApplicationAsync(Platform.CreateApplication("name", "companyName", "mailingAddress")).await()
    }

    @Test
    fun shouldListApplications() = runTest {
        platform.listApplications()
    }

    @Test
    fun shouldListApplicationsAsync() = runTest {
        platform.listApplicationsAsync().await()
    }

    @Test
    fun shouldGetApplication() = runTest {
        platform.getApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetApplicationAsync() = runTest {
        platform.getApplicationAsync(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        platform.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationNameAsync() = runTest {
        platform.updateApplicationNameAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        platform.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyNameAsync() = runTest {
        platform.updateApplicationCompanyNameAsync(DEFAULT_APPLICATION_ID, "").await()
    }
    
    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest { 
        platform.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddressAsync() = runTest {
        platform.updateApplicationMailingAddressAsync(DEFAULT_APPLICATION_ID, "").await()
    }
    
    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest { 
        platform.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicyAsync() = runTest {
        platform.updateApplicationPrivacyPolicyAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest { 
        platform.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContactAsync() = runTest {
        platform.updateApplicationSupportContactAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        platform.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLinkAsync() = runTest {
        platform.updateApplicationAppLinkAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        platform.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferencesAsync() = runTest {
        platform.updateApplicationEmailPreferencesAsync(DEFAULT_APPLICATION_ID, Platform.EmailPreferences()).await()
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        platform.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationLogoUrlAsync() = runTest {
        platform.updateApplicationLogoUrlAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        platform.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldDeleteApplicationAsync() = runTest {
        platform.deleteApplicationAsync(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        platform.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldGetLogoUploadUrlAsync() = runTest {
        platform.getLogoUploadUrlAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        platform.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthKeyAsync() = runTest {
        platform.addAuthKeyAsync(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = "")).await()
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        platform.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddAuthIssuerAsync() = runTest {
        platform.addAuthIssuerAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        platform.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuerAsync() = runTest {
        platform.deleteAuthIssuerAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        platform.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomainAsync() = runTest {
        platform.addCorsDomainAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        platform.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomainAsync() = runTest {
        platform.removeCorsDomainAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        platform.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwnerAsync() = runTest {
        platform.addApplicationOwnerAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        platform.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwnerAsync() = runTest {
        platform.removeApplicationOwnerAsync(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        platform.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetApplicationOwnersDetailsAsync() = runTest {
        platform.getApplicationOwnersDetailsAsync(DEFAULT_APPLICATION_ID).await()
    }
}