package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse

internal class PlatformResourceImpl(
    private val platformClient: PlatformClient
) : PlatformResource {

    override suspend fun createApplication(application: Platform.CreateApplication) {
        return platformClient.createApplicationRequest(application)
    }

    override suspend fun listApplications(): List<ApplicationResponse> {
        return platformClient.listApplicationsRequest()
    }

    override suspend fun getApplication(applicationId: String): ApplicationResponse {
        return platformClient.getApplicationRequest(applicationId)
    }

    override suspend fun updateApplicationName(applicationId: String, name: String) {
        return platformClient.updateApplicationNameRequest(applicationId, name)
    }

    override suspend fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return platformClient.updateApplicationCompanyNameRequest(applicationId, companyName)
    }

    override suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return platformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress)
    }

    override suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return platformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy)
    }

    override suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return platformClient.updateApplicationSupportContactRequest(applicationId, supportContact)
    }

    override suspend fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return platformClient.updateApplicationAppLinkRequest(applicationId, appLink)
    }

    override suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return platformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences)
    }

    override suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return platformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl)
    }

    override suspend fun deleteApplication(applicationId: String) {
        return platformClient.deleteApplicationRequest(applicationId)
    }

    override suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return platformClient.getLogoUploadUrlRequest(applicationId, contentType)
    }

    override suspend fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return platformClient.addAuthKeyRequest(applicationId, key)
    }

    override suspend fun addAuthIssuer(applicationId: String, url: String) {
        return platformClient.addAuthIssuerRequest(applicationId, url)
    }

    override suspend fun deleteAuthIssuer(applicationId: String, url: String) {
        return platformClient.deleteAuthIssuerRequest(applicationId, url)
    }

    override suspend fun addCorsDomain(applicationId: String, url: String) {
        return platformClient.addCorsDomainRequest(applicationId, url)
    }

    override suspend fun removeCorsDomain(applicationId: String, url: String) {
        return platformClient.removeCorsDomainRequest(applicationId, url)
    }

    override suspend fun addApplicationOwner(applicationId: String, userId: String) {
        return platformClient.addApplicationOwnerRequest(applicationId, userId)
    }

    override suspend fun removeApplicationOwner(applicationId: String, userId: String) {
        return platformClient.removeApplicationOwnerRequest(applicationId, userId)
    }

    override suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return platformClient.getApplicationOwnersDetailsRequest(applicationId)
    }
}