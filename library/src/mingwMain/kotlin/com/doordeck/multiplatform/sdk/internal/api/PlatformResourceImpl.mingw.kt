package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.*
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

class PlatformResourceImpl(
    httpClient: HttpClient
) : PlatformClient(httpClient), PlatformResource {

    override fun createApplication(application: Platform.CreateApplication) {
        return runBlocking { createApplicationRequest(application) }
    }

    override fun createApplicationJson(data: String) {
        val createApplicationData = data.fromJson<CreateApplicationData>()
        return createApplication(createApplicationData.toCreateApplication())
    }

    override fun listApplications(): List<ApplicationResponse> {
        return runBlocking { listApplicationsRequest() }
    }

    override fun listApplicationsJson(): String {
        return listApplications().toJson()
    }

    override fun getApplication(applicationId: String): ApplicationResponse {
        return runBlocking { getApplicationRequest(applicationId) }
    }

    override fun getApplicationJson(data: String): String {
        val getApplicationData = data.fromJson<GetApplicationData>()
        return getApplication(getApplicationData.applicationId).toJson()
    }

    override fun updateApplicationName(applicationId: String, name: String) {
        return runBlocking { updateApplicationNameRequest(applicationId, name) }
    }

    override fun updateApplicationNameJson(data: String) {
        val updateApplicationName = data.fromJson<UpdateApplicationNameData>()
        return updateApplicationName(updateApplicationName.applicationId, updateApplicationName.name)
    }

    override fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return runBlocking { updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override fun updateApplicationCompanyNameJson(data: String) {
        val updateApplicationCompanyName = data.fromJson<UpdateApplicationCompanyNameData>()
        return updateApplicationCompanyName(updateApplicationCompanyName.applicationId, updateApplicationCompanyName.companyName)
    }

    override fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return runBlocking { updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override fun updateApplicationMailingAddressJson(data: String) {
        val updateApplicationMailingAddressData = data.fromJson<UpdateApplicationMailingAddressData>()
        return updateApplicationMailingAddress(updateApplicationMailingAddressData.applicationId, updateApplicationMailingAddressData.mailingAddress)
    }

    override fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return runBlocking { updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override fun updateApplicationPrivacyPolicyJson(data: String) {
        val updateApplicationPrivacyPolicyData = data.fromJson<UpdateApplicationPrivacyPolicyData>()
        return updateApplicationPrivacyPolicy(updateApplicationPrivacyPolicyData.applicationId, updateApplicationPrivacyPolicyData.privacyPolicy)
    }

    override fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return runBlocking { updateApplicationSupportContactRequest(applicationId, supportContact) }
    }

    override fun updateApplicationSupportContactJson(data: String) {
        val updateApplicationSupportContactData = data.fromJson<UpdateApplicationSupportContactData>()
        return updateApplicationSupportContact(updateApplicationSupportContactData.applicationId, updateApplicationSupportContactData.supportContact)
    }

    override fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return runBlocking { updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override fun updateApplicationAppLinkJson(data: String) {
        val updateApplicationAppLinkData = data.fromJson<UpdateApplicationAppLinkData>()
        return updateApplicationAppLink(updateApplicationAppLinkData.applicationId, updateApplicationAppLinkData.appLink)
    }

    override fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return runBlocking { updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override fun updateApplicationEmailPreferencesJson(data: String) {
        val updateApplicationEmailPreferencesData = data.fromJson<UpdateApplicationEmailPreferencesData>()
        return updateApplicationEmailPreferences(updateApplicationEmailPreferencesData.applicationId, updateApplicationEmailPreferencesData.emailPreferences.toEmailPreferences())
    }

    override fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return runBlocking { updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override fun updateApplicationLogoUrlJson(data: String) {
        val updateApplicationLogoUrlData = data.fromJson<UpdateApplicationLogoUrlData>()
        return updateApplicationLogoUrl(updateApplicationLogoUrlData.applicationId, updateApplicationLogoUrlData.logoUrl)
    }

    override fun deleteApplication(applicationId: String) {
        return runBlocking { deleteApplicationRequest(applicationId) }
    }

    override fun deleteApplicationJson(data: String) {
        val deleteApplicationData = data.fromJson<DeleteApplicationData>()
        return deleteApplication(deleteApplicationData.applicationId)
    }

    override fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return runBlocking { getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override fun getLogoUploadUrlJson(data: String): String {
        val getLogoUploadUrlData = data.fromJson<GetLogoUploadUrlData>()
        return getLogoUploadUrl(getLogoUploadUrlData.applicationId, getLogoUploadUrlData.contentType).toJson()
    }

    override fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return runBlocking { addAuthKeyRequest(applicationId, key) }
    }

    override fun addAuthKeyJson(data: String) {
        TODO("Not yet implemented")
    }

    override fun addAuthIssuer(applicationId: String, url: String) {
        return runBlocking { addAuthIssuerRequest(applicationId, url) }
    }

    override fun addAuthIssuerJson(data: String) {
        val addAuthIssuerData = data.fromJson<AddAuthIssuerData>()
        return addAuthIssuer(addAuthIssuerData.applicationId, addAuthIssuerData.url)
    }

    override fun deleteAuthIssuer(applicationId: String, url: String) {
        return runBlocking { deleteAuthIssuerRequest(applicationId, url) }
    }

    override fun deleteAuthIssuer(data: String) {
        val deleteAuthIssuerData = data.fromJson<DeleteAuthIssuerData>()
        return deleteAuthIssuer(deleteAuthIssuerData.applicationId, deleteAuthIssuerData.url)
    }

    override fun addCorsDomain(applicationId: String, url: String) {
        return runBlocking { addCorsDomainRequest(applicationId, url) }
    }

    override fun addCorsDomain(data: String) {
        val addCorsDomainData = data.fromJson<AddCorsDomainData>()
        return addCorsDomain(addCorsDomainData.applicationId, addCorsDomainData.url)
    }

    override fun removeCorsDomain(applicationId: String, url: String) {
        return runBlocking { removeCorsDomainRequest(applicationId, url) }
    }

    override fun removeCorsDomainJson(data: String) {
        val removeCorsDomainData = data.fromJson<RemoveCorsDomainData>()
        return removeCorsDomain(removeCorsDomainData.applicationId, removeCorsDomainData.url)
    }

    override fun addApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { addApplicationOwnerRequest(applicationId, userId) }
    }

    override fun addApplicationOwnerJson(data: String) {
        val addApplicationOwnerData = data.fromJson<AddApplicationOwnerData>()
        return addApplicationOwner(addApplicationOwnerData.applicationId, addApplicationOwnerData.userId)
    }

    override fun removeApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { removeApplicationOwnerRequest(applicationId, userId) }
    }

    override fun removeApplicationOwnerJson(data: String) {
        val removeApplicationOwnerData = data.fromJson<RemoveApplicationOwnerData>()
        return removeApplicationOwner(removeApplicationOwnerData.applicationId, removeApplicationOwnerData.userId)
    }

    override fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return runBlocking { getApplicationOwnersDetailsRequest(applicationId) }
    }

    override fun getApplicationOwnersDetailsJson(data: String): String {
        val getApplicationOwnersDetailsData = data.fromJson<GetApplicationOwnersDetailsData>()
        return getApplicationOwnersDetails(getApplicationOwnersDetailsData.applicationId).toJson()
    }
}