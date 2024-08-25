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
    fun updateApplicationAppLink() = runTest {
        platform.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun updateApplicationAppLinkFuture() = runTest {
        platform.updateApplicationAppLinkFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun updateApplicationEmailPreferences() = runTest {
        platform.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun updateApplicationEmailPreferencesFuture() = runTest {
        platform.updateApplicationEmailPreferencesFuture(DEFAULT_APPLICATION_ID, Platform.EmailPreferences()).await()
    }

    @Test
    fun updateApplicationLogoUrl() = runTest {
        platform.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun updateApplicationLogoUrlFuture() = runTest {
        platform.updateApplicationLogoUrlFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun deleteApplication() = runTest {
        platform.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun deleteApplicationFuture() = runTest {
        platform.deleteApplicationFuture(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun getLogoUploadUrl() = runTest {
        platform.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun getLogoUploadUrlFuture() = runTest {
        platform.getLogoUploadUrlFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun addAuthKey() = runTest {
        platform.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun addAuthKeyFuture() = runTest {
        platform.addAuthKeyFuture(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = "")).await()
    }

    @Test
    fun addAuthIssuer() = runTest {
        platform.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun addAuthIssuerFuture() = runTest {
        platform.addAuthIssuerFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun deleteAuthIssuer() = runTest {
        platform.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun deleteAuthIssuerFuture() = runTest {
        platform.deleteAuthIssuerFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun addCorsDomain() = runTest {
        platform.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun addCorsDomainFuture() = runTest {
        platform.addCorsDomainFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun removeCorsDomain() = runTest {
        platform.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun removeCorsDomainFuture() = runTest {
        platform.removeCorsDomainFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun addApplicationOwner() = runTest {
        platform.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun addApplicationOwnerFuture() = runTest {
        platform.addApplicationOwnerFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun removeApplicationOwner() = runTest {
        platform.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun removeApplicationOwnerFuture() = runTest {
        platform.removeApplicationOwnerFuture(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun getApplicationOwnersDetails() = runTest {
        platform.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun getApplicationOwnersDetailsFuture() = runTest {
        platform.getApplicationOwnersDetailsFuture(DEFAULT_APPLICATION_ID).await()
    }
}