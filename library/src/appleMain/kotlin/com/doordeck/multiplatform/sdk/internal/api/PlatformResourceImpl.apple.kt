package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import io.ktor.client.HttpClient

class PlatformResourceImpl(
    httpClient: HttpClient
) : PlatformClient(httpClient), PlatformResource {

    override suspend fun createApplication(application: Platform.CreateApplication) {
        return createApplicationRequest(application)
    }

    override suspend fun listApplications(): List<ApplicationResponse> {
        return listApplicationsRequest()
    }

    override suspend fun getApplication(applicationId: String): ApplicationResponse {
        return getApplicationRequest(applicationId)
    }

    override suspend fun updateApplicationName(applicationId: String, name: String) {
        return updateApplicationNameRequest(applicationId, name)
    }

    override suspend fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return updateApplicationCompanyNameRequest(applicationId, companyName)
    }

    override suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return updateApplicationMailingAddressRequest(applicationId, mailingAddress)
    }

    override suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy)
    }

    override suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return updateApplicationSupportContactRequest(applicationId, supportContact)
    }

    override suspend fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return updateApplicationAppLinkRequest(applicationId, appLink)
    }

    override suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return updateApplicationEmailPreferencesRequest(applicationId, emailPreferences)
    }

    override suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return updateApplicationLogoUrlRequest(applicationId, logoUrl)
    }

    override suspend fun deleteApplication(applicationId: String) {
        return deleteApplicationRequest(applicationId)
    }

    override suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return getLogoUploadUrlRequest(applicationId, contentType)
    }

    override suspend fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return addAuthKeyRequest(applicationId, key)
    }

    override suspend fun addAuthIssuer(applicationId: String, url: String) {
        return addAuthIssuerRequest(applicationId, url)
    }

    override suspend fun deleteAuthIssuer(applicationId: String, url: String) {
        return deleteAuthIssuerRequest(applicationId, url)
    }

    override suspend fun addCorsDomain(applicationId: String, url: String) {
        return addCorsDomainRequest(applicationId, url)
    }

    override suspend fun removeCorsDomain(applicationId: String, url: String) {
        return removeCorsDomainRequest(applicationId, url)
    }

    override suspend fun addApplicationOwner(applicationId: String, userId: String) {
        return addApplicationOwnerRequest(applicationId, userId)
    }

    override suspend fun removeApplicationOwner(applicationId: String, userId: String) {
        return removeApplicationOwnerRequest(applicationId, userId)
    }

    override suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return getApplicationOwnersDetailsRequest(applicationId)
    }
}