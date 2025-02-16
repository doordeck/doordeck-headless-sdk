package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.model.data.AddApplicationOwnerData
import com.doordeck.multiplatform.sdk.model.data.AddAuthIssuerData
import com.doordeck.multiplatform.sdk.model.data.AddAuthKeyData
import com.doordeck.multiplatform.sdk.model.data.AddCorsDomainData
import com.doordeck.multiplatform.sdk.model.data.CreateApplicationData
import com.doordeck.multiplatform.sdk.model.data.DeleteApplicationData
import com.doordeck.multiplatform.sdk.model.data.DeleteAuthIssuerData
import com.doordeck.multiplatform.sdk.model.data.EcKeyData
import com.doordeck.multiplatform.sdk.model.data.EmailPreferencesData
import com.doordeck.multiplatform.sdk.model.data.GetApplicationData
import com.doordeck.multiplatform.sdk.model.data.GetApplicationOwnersDetailsData
import com.doordeck.multiplatform.sdk.model.data.GetLogoUploadUrlData
import com.doordeck.multiplatform.sdk.model.data.Platform
import com.doordeck.multiplatform.sdk.model.data.RemoveApplicationOwnerData
import com.doordeck.multiplatform.sdk.model.data.RemoveCorsDomainData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationAppLinkData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationCompanyNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationEmailPreferencesData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationLogoUrlData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationMailingAddressData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationPrivacyPolicyData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationSupportContactData
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformApiTest : MockTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        PlatformApi.createApplication(Platform.CreateApplication("name", "companyName", "mailingAddress"))
    }

    @Test
    fun shouldCreateApplicationJson() = runTest {
        PlatformApi.createApplicationJson(CreateApplicationData("name", "companyName", "mailingAddress").toJson())
    }

    @Test
    fun shouldListApplications() = runTest {
        val response = PlatformApi.listApplications()
        assertEquals(APPLICATION_LIST_RESPONSE, response)
    }

    @Test
    fun shouldListApplicationsJson() = runTest {
        val response = PlatformApi.listApplicationsJson()
        assertEquals(APPLICATION_LIST_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetApplication() = runTest {
        val response = PlatformApi.getApplication(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_RESPONSE, response)
    }

    @Test
    fun shouldGetApplicationJson() = runTest {
        val response = PlatformApi.getApplicationJson(GetApplicationData(DEFAULT_APPLICATION_ID).toJson())
        assertEquals(APPLICATION_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        PlatformApi.updateApplicationName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationNameJson() = runTest {
        PlatformApi.updateApplicationNameJson(UpdateApplicationNameData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        PlatformApi.updateApplicationCompanyName(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationCompanyNameJson() = runTest {
        PlatformApi.updateApplicationCompanyNameJson(UpdateApplicationCompanyNameData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        PlatformApi.updateApplicationMailingAddress(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationMailingAddressJson() = runTest {
        PlatformApi.updateApplicationMailingAddressJson(UpdateApplicationMailingAddressData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        PlatformApi.updateApplicationPrivacyPolicy(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicyJson() = runTest {
        PlatformApi.updateApplicationPrivacyPolicyJson(UpdateApplicationPrivacyPolicyData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        PlatformApi.updateApplicationSupportContact(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationSupportContactJson() = runTest {
        PlatformApi.updateApplicationSupportContactJson(UpdateApplicationSupportContactData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        PlatformApi.updateApplicationAppLink(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationAppLinkJson() = runTest {
        PlatformApi.updateApplicationAppLinkJson(UpdateApplicationAppLinkData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        PlatformApi.updateApplicationEmailPreferences(DEFAULT_APPLICATION_ID, Platform.EmailPreferences())
    }

    @Test
    fun shouldUpdateApplicationEmailPreferencesJson() = runTest {
        PlatformApi.updateApplicationEmailPreferencesJson(UpdateApplicationEmailPreferencesData(DEFAULT_APPLICATION_ID, EmailPreferencesData()).toJson())
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        PlatformApi.updateApplicationLogoUrl(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldUpdateApplicationLogoUrlJson() = runTest {
        PlatformApi.updateApplicationLogoUrlJson(UpdateApplicationLogoUrlData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        PlatformApi.deleteApplication(DEFAULT_APPLICATION_ID)
    }

    @Test
    fun shouldDeleteApplicationJson() = runTest {
        PlatformApi.deleteApplicationJson(DeleteApplicationData(DEFAULT_APPLICATION_ID).toJson())
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        val response = PlatformApi.getLogoUploadUrl(DEFAULT_APPLICATION_ID, "")
        assertEquals(LOGO_UPLOAD_URL_RESPONSE, response)
    }

    @Test
    fun shouldGetLogoUploadUrlJson() = runTest {
        val response = PlatformApi.getLogoUploadUrlJson(GetLogoUploadUrlData(DEFAULT_APPLICATION_ID, "").toJson())
        assertEquals(LOGO_UPLOAD_URL_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        PlatformApi.addAuthKey(DEFAULT_APPLICATION_ID, Platform.EcKey(use = "", kid = "", d = "", crv = "", x = "", y = ""))
    }

    @Test
    fun shouldAddAuthKeyJson() = runTest {
        PlatformApi.addAuthKeyJson(AddAuthKeyData(DEFAULT_APPLICATION_ID, EcKeyData(use = "", kid = "", d = "", crv = "", x = "", y = "")).toJson())
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        PlatformApi.addAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddAuthIssuerJson() = runTest {
        PlatformApi.addAuthIssuerJson(AddAuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        PlatformApi.deleteAuthIssuer(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldDeleteAuthIssuerJson() = runTest {
        PlatformApi.deleteAuthIssuerJson(DeleteAuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        PlatformApi.addCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddCorsDomainJson() = runTest {
        PlatformApi.addCorsDomainJson(AddCorsDomainData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        PlatformApi.removeCorsDomain(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveCorsDomainJson() = runTest {
        PlatformApi.removeCorsDomainJson(RemoveCorsDomainData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        PlatformApi.addApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldAddApplicationOwnerJson() = runTest {
        PlatformApi.addApplicationOwnerJson(AddApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        PlatformApi.removeApplicationOwner(DEFAULT_APPLICATION_ID, "")
    }

    @Test
    fun shouldRemoveApplicationOwnerJson() = runTest {
        PlatformApi.removeApplicationOwnerJson(RemoveApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson())
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        val response = PlatformApi.getApplicationOwnersDetails(DEFAULT_APPLICATION_ID)
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE, response)
    }

    @Test
    fun shouldGetApplicationOwnersDetailsJson() = runTest {
        val response = PlatformApi.getApplicationOwnersDetailsJson(GetApplicationOwnersDetailsData(DEFAULT_APPLICATION_ID).toJson())
        assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE.toResultDataJson(), response)
    }
}