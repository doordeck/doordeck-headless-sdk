package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
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
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformResourceImplTest : MockTest() {

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
        val response = PlatformResourceImpl.listApplications()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldListApplicationsJson() = runTest {
        val response = PlatformResourceImpl.listApplicationsJson()
        assertEquals(APPLICATION_LIST_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetApplication() = runTest {
        val response = PlatformResourceImpl.getApplication(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldGetApplicationJson() = runTest {
        val response = PlatformResourceImpl.getApplicationJson(GetApplicationData(DEFAULT_APPLICATION_ID).toJson())
        assertEquals(APPLICATION_RESPONSE.toResultDataJson(), response)
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
        val response = PlatformResourceImpl.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldGetLogoUploadUrlJson() = runTest {
        val response = PlatformResourceImpl.getLogoUploadUrlJson(GetLogoUploadUrlData(DEFAULT_APPLICATION_ID, "").toJson())
        assertEquals(LOGO_UPLOAD_URL_RESPONSE.toResultDataJson(), response)
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
        val response = PlatformResourceImpl.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldGetApplicationOwnersDetailsJson() = runTest {
        val response = PlatformResourceImpl.getApplicationOwnersDetailsJson(GetApplicationOwnersDetailsData(DEFAULT_APPLICATION_ID).toJson())
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE.toResultDataJson(), response)
    }
}