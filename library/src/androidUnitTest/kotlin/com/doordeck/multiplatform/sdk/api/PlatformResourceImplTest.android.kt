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
    fun shouldCreateApplicationFuture() = runTest {
        platform.createApplicationFuture(Platform.CreateApplication("name", "companyName", "mailingAddress")).await()
    }

    @Test
    fun shouldListApplications() = runTest {
        platform.listApplications()
    }

    @Test
    fun shouldListApplicationsFuture() = runTest {
        platform.listApplicationsFuture().await()
    }

    @Test
    fun shouldGetApplication() = runTest {
        platform.getApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetApplicationFuture() = runTest {
        platform.getApplicationFuture(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        platform.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationNameFuture() = runTest {
        platform.updateApplicationNameFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        platform.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyNameFuture() = runTest {
        platform.updateApplicationCompanyNameFuture(DEFAULT_APPLICATION_ID, "").await()
    }
    
    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest { 
        platform.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddressFuture() = runTest {
        platform.updateApplicationMailingAddressFuture(DEFAULT_APPLICATION_ID, "").await()
    }
    
    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest { 
        platform.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicyFuture() = runTest {
        platform.updateApplicationPrivacyPolicyFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest { 
        platform.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContactFuture() = runTest {
        platform.updateApplicationSupportContactFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        platform.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLinkFuture() = runTest {
        platform.updateApplicationAppLinkFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        platform.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferencesFuture() = runTest {
        platform.updateApplicationEmailPreferencesFuture(DEFAULT_APPLICATION_ID, Platform.EmailPreferences()).await()
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        platform.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationLogoUrlFuture() = runTest {
        platform.updateApplicationLogoUrlFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        platform.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldDeleteApplicationFuture() = runTest {
        platform.deleteApplicationFuture(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        platform.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldGetLogoUploadUrlFuture() = runTest {
        platform.getLogoUploadUrlFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        platform.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthKeyFuture() = runTest {
        platform.addAuthKeyFuture(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = "")).await()
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        platform.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddAuthIssuerFuture() = runTest {
        platform.addAuthIssuerFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        platform.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuerFuture() = runTest {
        platform.deleteAuthIssuerFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        platform.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomainFuture() = runTest {
        platform.addCorsDomainFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        platform.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomainFuture() = runTest {
        platform.removeCorsDomainFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        platform.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwnerFuture() = runTest {
        platform.addApplicationOwnerFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        platform.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwnerFuture() = runTest {
        platform.removeApplicationOwnerFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        platform.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetApplicationOwnersDetailsFuture() = runTest {
        platform.getApplicationOwnersDetailsFuture(DEFAULT_APPLICATION_ID).await()
    }
}