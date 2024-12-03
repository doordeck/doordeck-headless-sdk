package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.AddApplicationOwnerData
import com.doordeck.multiplatform.sdk.api.model.AddAuthIssuerData
import com.doordeck.multiplatform.sdk.api.model.AddAuthKeyData
import com.doordeck.multiplatform.sdk.api.model.AddCorsDomainData
import com.doordeck.multiplatform.sdk.api.model.CreateApplicationData
import com.doordeck.multiplatform.sdk.api.model.DeleteApplicationData
import com.doordeck.multiplatform.sdk.api.model.DeleteAuthIssuerData
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
import com.doordeck.multiplatform.sdk.api.model.toAuthKey
import com.doordeck.multiplatform.sdk.api.model.toCreateApplication
import com.doordeck.multiplatform.sdk.api.model.toEmailPreferences
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.runBlocking

internal class PlatformResourceImpl(
    private val platformClient: PlatformClient
) : PlatformResource {

    override fun createApplication(application: Platform.CreateApplication) {
        return runBlocking { platformClient.createApplicationRequest(application) }
    }

    override fun createApplicationJson(data: String) {
        val createApplicationData = data.fromJson<CreateApplicationData>()
        return createApplication(createApplicationData.toCreateApplication())
    }

    override fun listApplications(): List<ApplicationResponse> {
        return runBlocking { platformClient.listApplicationsRequest() }
    }

    override fun listApplicationsJson(): String {
        return listApplications().toJson()
    }

    override fun getApplication(applicationId: String): ApplicationResponse {
        return runBlocking { platformClient.getApplicationRequest(applicationId) }
    }

    override fun getApplicationJson(data: String): String {
        val getApplicationData = data.fromJson<GetApplicationData>()
        return getApplication(getApplicationData.applicationId).toJson()
    }

    override fun updateApplicationName(applicationId: String, name: String) {
        return runBlocking { platformClient.updateApplicationNameRequest(applicationId, name) }
    }

    override fun updateApplicationNameJson(data: String) {
        val updateApplicationName = data.fromJson<UpdateApplicationNameData>()
        return updateApplicationName(updateApplicationName.applicationId, updateApplicationName.name)
    }

    override fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return runBlocking { platformClient.updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override fun updateApplicationCompanyNameJson(data: String) {
        val updateApplicationCompanyName = data.fromJson<UpdateApplicationCompanyNameData>()
        return updateApplicationCompanyName(updateApplicationCompanyName.applicationId, updateApplicationCompanyName.companyName)
    }

    override fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return runBlocking { platformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override fun updateApplicationMailingAddressJson(data: String) {
        val updateApplicationMailingAddressData = data.fromJson<UpdateApplicationMailingAddressData>()
        return updateApplicationMailingAddress(updateApplicationMailingAddressData.applicationId, updateApplicationMailingAddressData.mailingAddress)
    }

    override fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return runBlocking { platformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override fun updateApplicationPrivacyPolicyJson(data: String) {
        val updateApplicationPrivacyPolicyData = data.fromJson<UpdateApplicationPrivacyPolicyData>()
        return updateApplicationPrivacyPolicy(updateApplicationPrivacyPolicyData.applicationId, updateApplicationPrivacyPolicyData.privacyPolicy)
    }

    override fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return runBlocking { platformClient.updateApplicationSupportContactRequest(applicationId, supportContact) }
    }

    override fun updateApplicationSupportContactJson(data: String) {
        val updateApplicationSupportContactData = data.fromJson<UpdateApplicationSupportContactData>()
        return updateApplicationSupportContact(updateApplicationSupportContactData.applicationId, updateApplicationSupportContactData.supportContact)
    }

    override fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return runBlocking { platformClient.updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override fun updateApplicationAppLinkJson(data: String) {
        val updateApplicationAppLinkData = data.fromJson<UpdateApplicationAppLinkData>()
        return updateApplicationAppLink(updateApplicationAppLinkData.applicationId, updateApplicationAppLinkData.appLink)
    }

    override fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return runBlocking { platformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override fun updateApplicationEmailPreferencesJson(data: String) {
        val updateApplicationEmailPreferencesData = data.fromJson<UpdateApplicationEmailPreferencesData>()
        return updateApplicationEmailPreferences(updateApplicationEmailPreferencesData.applicationId, updateApplicationEmailPreferencesData.emailPreferences.toEmailPreferences())
    }

    override fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return runBlocking { platformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override fun updateApplicationLogoUrlJson(data: String) {
        val updateApplicationLogoUrlData = data.fromJson<UpdateApplicationLogoUrlData>()
        return updateApplicationLogoUrl(updateApplicationLogoUrlData.applicationId, updateApplicationLogoUrlData.logoUrl)
    }

    override fun deleteApplication(applicationId: String) {
        return runBlocking { platformClient.deleteApplicationRequest(applicationId) }
    }

    override fun deleteApplicationJson(data: String) {
        val deleteApplicationData = data.fromJson<DeleteApplicationData>()
        return deleteApplication(deleteApplicationData.applicationId)
    }

    override fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return runBlocking { platformClient.getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override fun getLogoUploadUrlJson(data: String): String {
        val getLogoUploadUrlData = data.fromJson<GetLogoUploadUrlData>()
        return getLogoUploadUrl(getLogoUploadUrlData.applicationId, getLogoUploadUrlData.contentType).toJson()
    }

    override fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return runBlocking { platformClient.addAuthKeyRequest(applicationId, key) }
    }

    override fun addAuthKeyJson(data: String) {
        val addAuthKeyData = data.fromJson<AddAuthKeyData>()
        return addAuthKey(addAuthKeyData.applicationId, addAuthKeyData.key.toAuthKey())
    }

    override fun addAuthIssuer(applicationId: String, url: String) {
        return runBlocking { platformClient.addAuthIssuerRequest(applicationId, url) }
    }

    override fun addAuthIssuerJson(data: String) {
        val addAuthIssuerData = data.fromJson<AddAuthIssuerData>()
        return addAuthIssuer(addAuthIssuerData.applicationId, addAuthIssuerData.url)
    }

    override fun deleteAuthIssuer(applicationId: String, url: String) {
        return runBlocking { platformClient.deleteAuthIssuerRequest(applicationId, url) }
    }

    override fun deleteAuthIssuerJson(data: String) {
        val deleteAuthIssuerData = data.fromJson<DeleteAuthIssuerData>()
        return deleteAuthIssuer(deleteAuthIssuerData.applicationId, deleteAuthIssuerData.url)
    }

    override fun addCorsDomain(applicationId: String, url: String) {
        return runBlocking { platformClient.addCorsDomainRequest(applicationId, url) }
    }

    override fun addCorsDomainJson(data: String) {
        val addCorsDomainData = data.fromJson<AddCorsDomainData>()
        return addCorsDomain(addCorsDomainData.applicationId, addCorsDomainData.url)
    }

    override fun removeCorsDomain(applicationId: String, url: String) {
        return runBlocking { platformClient.removeCorsDomainRequest(applicationId, url) }
    }

    override fun removeCorsDomainJson(data: String) {
        val removeCorsDomainData = data.fromJson<RemoveCorsDomainData>()
        return removeCorsDomain(removeCorsDomainData.applicationId, removeCorsDomainData.url)
    }

    override fun addApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { platformClient.addApplicationOwnerRequest(applicationId, userId) }
    }

    override fun addApplicationOwnerJson(data: String) {
        val addApplicationOwnerData = data.fromJson<AddApplicationOwnerData>()
        return addApplicationOwner(addApplicationOwnerData.applicationId, addApplicationOwnerData.userId)
    }

    override fun removeApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { platformClient.removeApplicationOwnerRequest(applicationId, userId) }
    }

    override fun removeApplicationOwnerJson(data: String) {
        val removeApplicationOwnerData = data.fromJson<RemoveApplicationOwnerData>()
        return removeApplicationOwner(removeApplicationOwnerData.applicationId, removeApplicationOwnerData.userId)
    }

    override fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return runBlocking { platformClient.getApplicationOwnersDetailsRequest(applicationId) }
    }

    override fun getApplicationOwnersDetailsJson(data: String): String {
        val getApplicationOwnersDetailsData = data.fromJson<GetApplicationOwnersDetailsData>()
        return getApplicationOwnersDetails(getApplicationOwnersDetailsData.applicationId).toJson()
    }
}