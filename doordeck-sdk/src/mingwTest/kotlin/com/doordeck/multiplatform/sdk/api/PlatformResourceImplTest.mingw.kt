package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.api.model.AddApplicationOwnerData
import com.doordeck.multiplatform.sdk.api.model.AddAuthIssuerData
import com.doordeck.multiplatform.sdk.api.model.AddAuthKeyData
import com.doordeck.multiplatform.sdk.api.model.AddCorsDomainData
import com.doordeck.multiplatform.sdk.api.model.CreateApplicationData
import com.doordeck.multiplatform.sdk.api.model.DeleteApplicationData
import com.doordeck.multiplatform.sdk.api.model.DeleteAuthIssuerData
import com.doordeck.multiplatform.sdk.api.model.EcKeyData
import com.doordeck.multiplatform.sdk.api.model.EmailPreferencesData
import com.doordeck.multiplatform.sdk.api.model.GetApplicationData
import com.doordeck.multiplatform.sdk.api.model.GetApplicationOwnersDetailsData
import com.doordeck.multiplatform.sdk.api.model.GetLogoUploadUrlData
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.model.RemoveApplicationOwnerData
import com.doordeck.multiplatform.sdk.api.model.RemoveCorsDomainData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationAppLinkData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationCompanyNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationEmailPreferencesData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationLogoUrlData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationMailingAddressData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationPrivacyPolicyData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationSupportContactData
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PlatformResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        CloudHttpClient.overrideClient(TEST_HTTP_CLIENT)
    }

    @Test
    fun shouldCreateApplication() = runTest {
        PlatformResourceImpl.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldCreateApplicationJson() = runTest {
        PlatformResourceImpl.createApplicationJson(CreateApplicationData("name", "companyName", "mailingAddress").toJson())
    }

    @Test
    fun shouldListApplications() = runTest {
        PlatformResourceImpl.listApplications()
    }

    @Test
    fun shouldListApplicationsJson() = runTest {
        PlatformResourceImpl.listApplicationsJson()
    }

    @Test
    fun shouldGetApplication() = runTest {
        PlatformResourceImpl.getApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetApplicationJson() = runTest {
        PlatformResourceImpl.getApplicationJson(GetApplicationData(DEFAULT_APPLICATION_ID).toJson())
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        PlatformResourceImpl.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationNameJson() = runTest {
        PlatformResourceImpl.updateApplicationNameJson(UpdateApplicationNameData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        PlatformResourceImpl.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyNameJson() = runTest {
        PlatformResourceImpl.updateApplicationCompanyNameJson(UpdateApplicationCompanyNameData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        PlatformResourceImpl.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddressJson() = runTest {
        PlatformResourceImpl.updateApplicationMailingAddressJson(UpdateApplicationMailingAddressData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        PlatformResourceImpl.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicyJson() = runTest {
        PlatformResourceImpl.updateApplicationPrivacyPolicyJson(UpdateApplicationPrivacyPolicyData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        PlatformResourceImpl.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContactJson() = runTest {
        PlatformResourceImpl.updateApplicationSupportContactJson(UpdateApplicationSupportContactData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        PlatformResourceImpl.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLinkJson() = runTest {
        PlatformResourceImpl.updateApplicationAppLinkJson(UpdateApplicationAppLinkData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        PlatformResourceImpl.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferencesJson() = runTest {
        PlatformResourceImpl.updateApplicationEmailPreferencesJson(UpdateApplicationEmailPreferencesData(DEFAULT_APPLICATION_ID, EmailPreferencesData()).toJson())
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        PlatformResourceImpl.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationLogoUrlJson() = runTest {
        PlatformResourceImpl.updateApplicationLogoUrlJson(UpdateApplicationLogoUrlData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        PlatformResourceImpl.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldDeleteApplicationJson() = runTest {
        PlatformResourceImpl.deleteApplicationJson(DeleteApplicationData(DEFAULT_APPLICATION_ID).toJson())
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        PlatformResourceImpl.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldGetLogoUploadUrlJson() = runTest {
        PlatformResourceImpl.getLogoUploadUrlJson(GetLogoUploadUrlData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        PlatformResourceImpl.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthKeyJson() = runTest {
        PlatformResourceImpl.addAuthKeyJson(AddAuthKeyData(DEFAULT_APPLICATION_ID, EcKeyData(use = "", kid = "", d = "", crv = "", x = "", y = "")).toJson())
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        PlatformResourceImpl.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddAuthIssuerJson() = runTest {
        PlatformResourceImpl.addAuthIssuerJson(AddAuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        PlatformResourceImpl.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuerJson() = runTest {
        PlatformResourceImpl.deleteAuthIssuerJson(DeleteAuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        PlatformResourceImpl.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomainJson() = runTest {
        PlatformResourceImpl.addCorsDomainJson(AddCorsDomainData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        PlatformResourceImpl.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomainJson() = runTest {
        PlatformResourceImpl.removeCorsDomainJson(RemoveCorsDomainData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        PlatformResourceImpl.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwnerJson() = runTest {
        PlatformResourceImpl.addApplicationOwnerJson(AddApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        PlatformResourceImpl.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwnerJson() = runTest {
        PlatformResourceImpl.removeApplicationOwnerJson(RemoveApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        PlatformResourceImpl.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetApplicationOwnersDetailsJson() = runTest {
        PlatformResourceImpl.getApplicationOwnersDetailsJson(GetApplicationOwnersDetailsData(DEFAULT_APPLICATION_ID).toJson())
    }
}