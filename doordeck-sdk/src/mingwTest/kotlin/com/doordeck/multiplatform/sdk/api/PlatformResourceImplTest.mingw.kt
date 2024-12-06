package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
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
import com.doordeck.multiplatform.sdk.internal.api.PlatformClient
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PlatformResourceImplTest {
    
    private val platform = PlatformResourceImpl(PlatformClient(TEST_HTTP_CLIENT))

    @Test
    fun shouldCreateApplication() = runTest {
        platform.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldCreateApplicationJson() = runTest {
        platform.createApplicationJson(CreateApplicationData("name", "companyName", "mailingAddress").toJson())
    }

    @Test
    fun shouldListApplications() = runTest {
        platform.listApplications()
    }

    @Test
    fun shouldListApplicationsJson() = runTest {
        platform.listApplicationsJson()
    }

    @Test
    fun shouldGetApplication() = runTest {
        platform.getApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetApplicationJson() = runTest {
        platform.getApplicationJson(GetApplicationData(DEFAULT_APPLICATION_ID).toJson())
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        platform.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationNameJson() = runTest {
        platform.updateApplicationNameJson(UpdateApplicationNameData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        platform.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyNameJson() = runTest {
        platform.updateApplicationCompanyNameJson(UpdateApplicationCompanyNameData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        platform.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddressJson() = runTest {
        platform.updateApplicationMailingAddressJson(UpdateApplicationMailingAddressData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        platform.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicyJson() = runTest {
        platform.updateApplicationPrivacyPolicyJson(UpdateApplicationPrivacyPolicyData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        platform.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContactJson() = runTest {
        platform.updateApplicationSupportContactJson(UpdateApplicationSupportContactData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        platform.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLinkJson() = runTest {
        platform.updateApplicationAppLinkJson(UpdateApplicationAppLinkData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        platform.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferencesJson() = runTest {
        platform.updateApplicationEmailPreferencesJson(UpdateApplicationEmailPreferencesData(DEFAULT_APPLICATION_ID, EmailPreferencesData()).toJson())
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        platform.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationLogoUrlJson() = runTest {
        platform.updateApplicationLogoUrlJson(UpdateApplicationLogoUrlData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        platform.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldDeleteApplicationJson() = runTest {
        platform.deleteApplicationJson(DeleteApplicationData(DEFAULT_APPLICATION_ID).toJson())
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        platform.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldGetLogoUploadUrlJson() = runTest {
        platform.getLogoUploadUrlJson(GetLogoUploadUrlData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        platform.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthKeyJson() = runTest {
        platform.addAuthKeyJson(AddAuthKeyData(DEFAULT_APPLICATION_ID, EcKeyData(use = "", kid = "", d = "", crv = "", x = "", y = "")).toJson())
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        platform.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddAuthIssuerJson() = runTest {
        platform.addAuthIssuerJson(AddAuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        platform.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuerJson() = runTest {
        platform.deleteAuthIssuerJson(DeleteAuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        platform.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomainJson() = runTest {
        platform.addCorsDomainJson(AddCorsDomainData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        platform.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomainJson() = runTest {
        platform.removeCorsDomainJson(RemoveCorsDomainData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        platform.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwnerJson() = runTest {
        platform.addApplicationOwnerJson(AddApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        platform.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwnerJson() = runTest {
        platform.removeApplicationOwnerJson(RemoveApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        platform.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldGetApplicationOwnersDetailsJson() = runTest {
        platform.getApplicationOwnersDetailsJson(GetApplicationOwnersDetailsData(DEFAULT_APPLICATION_ID).toJson())
    }
}