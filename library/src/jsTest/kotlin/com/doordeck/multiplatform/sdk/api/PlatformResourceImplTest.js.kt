package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PlatformResourceImplTest {

    private val platform = PlatformResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldCreateApplication() = runTest {
        platform.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress")).await()
    }

    @Test
    fun shouldListApplications() = runTest {
        platform.listApplications().await()
    }

    @Test
    fun shouldGetApplication() = runTest {
        platform.getApplication(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        platform.updateApplicationName(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        platform.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "").await()
    }
    
    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest { 
        platform.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest { 
        platform.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest { 
        platform.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun updateApplicationAppLink() = runTest {
        platform.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun updateApplicationEmailPreferences() = runTest {
        platform.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences()).await()
    }

    @Test
    fun updateApplicationLogoUrl() = runTest {
        platform.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun deleteApplication() = runTest {
        platform.deleteApplication(DEFAULT_APPLICATION_ID).await()
    }

    @Test
    fun getLogoUploadUrl() = runTest {
        platform.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun addAuthKey() = runTest {
        platform.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = "")).await()
    }

    @Test
    fun addAuthIssuer() = runTest {
        platform.addAuthIssuer(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun deleteAuthIssuer() = runTest {
        platform.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun addCorsDomain() = runTest {
        platform.addCorsDomain(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun removeCorsDomain() = runTest {
        platform.removeCorsDomain(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun addApplicationOwner() = runTest {
        platform.addApplicationOwner(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun removeApplicationOwner() = runTest {
        platform.removeApplicationOwner(DEFAULT_APPLICATION_ID, "").await()
    }

    @Test
    fun getApplicationOwnersDetails() = runTest {
        platform.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID).await()
    }
}