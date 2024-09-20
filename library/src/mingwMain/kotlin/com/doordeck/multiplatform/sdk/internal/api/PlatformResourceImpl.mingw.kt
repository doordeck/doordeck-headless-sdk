package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

class PlatformResourceImpl(
    httpClient: HttpClient
) : PlatformClient(httpClient), PlatformResource {

    override fun createApplication(application: Platform.CreateApplication) {
        return runBlocking { createApplicationRequest(application) }
    }

    override fun listApplications(): List<ApplicationResponse> {
        return runBlocking { listApplicationsRequest() }
    }

    override fun getApplication(applicationId: String): ApplicationResponse {
        return runBlocking { getApplicationRequest(applicationId) }
    }

    override fun updateApplicationName(applicationId: String, name: String) {
        return runBlocking { updateApplicationNameRequest(applicationId, name) }
    }

    override fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return runBlocking { updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return runBlocking { updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return runBlocking { updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return runBlocking { updateApplicationSupportContactRequest(applicationId, supportContact) }
    }

    override fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return runBlocking { updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return runBlocking { updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return runBlocking { updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override fun deleteApplication(applicationId: String) {
        return runBlocking { deleteApplicationRequest(applicationId) }
    }

    override fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return runBlocking { getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return runBlocking { addAuthKeyRequest(applicationId, key) }
    }

    override fun addAuthIssuer(applicationId: String, url: String) {
        return runBlocking { addAuthIssuerRequest(applicationId, url) }
    }

    override fun deleteAuthIssuer(applicationId: String, url: String) {
        return runBlocking { deleteAuthIssuerRequest(applicationId, url) }
    }

    override fun addCorsDomain(applicationId: String, url: String) {
        return runBlocking { addCorsDomainRequest(applicationId, url) }
    }

    override fun removeCorsDomain(applicationId: String, url: String) {
        return runBlocking { removeCorsDomainRequest(applicationId, url) }
    }

    override fun addApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { addApplicationOwnerRequest(applicationId, userId) }
    }

    override fun removeApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { removeApplicationOwnerRequest(applicationId, userId) }
    }

    override fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return runBlocking { getApplicationOwnersDetailsRequest(applicationId) }
    }
}