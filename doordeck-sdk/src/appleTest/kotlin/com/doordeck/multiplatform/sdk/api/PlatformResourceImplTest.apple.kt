package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PlatformResourceImplTest {

    private val platform = PlatformResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldCreateApplication() = runTest {
        platform.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldListApplications() = runTest {
        platform.listApplications()
    }

    @Test
    fun shouldGetApplication() = runTest {
        platform.getApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        platform.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        platform.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest { 
        platform.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest { 
        platform.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest { 
        platform.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        platform.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        platform.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        platform.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        platform.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        platform.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        platform.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        platform.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        platform.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        platform.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        platform.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        platform.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        platform.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        platform.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
    }
}