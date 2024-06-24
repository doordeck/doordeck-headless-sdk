package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.PlatformResource
import com.doordeck.sdk.api.model.Platform
import com.doordeck.sdk.api.requests.AddApplicationOwnerRequest
import com.doordeck.sdk.api.requests.AddAuthIssuerRequest
import com.doordeck.sdk.api.requests.AddCorsDomainRequest
import com.doordeck.sdk.api.requests.CallToActionRequest
import com.doordeck.sdk.api.requests.DeleteAuthIssuerRequest
import com.doordeck.sdk.api.requests.EmailPreferencesRequest
import com.doordeck.sdk.api.requests.GetLogoUploadUrlRequest
import com.doordeck.sdk.api.requests.RemoveApplicationOwnerRequest
import com.doordeck.sdk.api.requests.RemoveCorsDomainRequest
import com.doordeck.sdk.api.requests.UpdateApplicationAppLinkRequest
import com.doordeck.sdk.api.requests.UpdateApplicationCompanyNameRequest
import com.doordeck.sdk.api.requests.UpdateApplicationEmailPreferencesRequest
import com.doordeck.sdk.api.requests.UpdateApplicationLogoUrlRequest
import com.doordeck.sdk.api.requests.UpdateApplicationMailingAddressRequest
import com.doordeck.sdk.api.requests.UpdateApplicationNameRequest
import com.doordeck.sdk.api.requests.UpdateApplicationPrivacyPolicyRequest
import com.doordeck.sdk.api.requests.UpdateApplicationRequest
import com.doordeck.sdk.api.requests.UpdateApplicationSupportContactRequest
import com.doordeck.sdk.api.requests.toAddAuthKeyRequest
import com.doordeck.sdk.api.requests.toCreateApplicationRequest
import com.doordeck.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.sdk.api.responses.ApplicationResponse
import com.doordeck.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class PlatformResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), PlatformResource {

    override fun createApplication(application: Platform.CreateApplication) {
        httpClient.postEmpty(Paths.getCreateApplicationPath()) {
            addRequestHeaders()
            setBody(application.toCreateApplicationRequest())
        }
    }

    override fun listApplications(): Array<ApplicationResponse> {
        return httpClient.get(Paths.getListApplicationsPath())
    }

    override fun getApplication(applicationId: String): ApplicationResponse {
        return httpClient.get(Paths.getApplicationPath(applicationId))
    }

    override fun updateApplicationName(applicationId: String, name: String) {
        updateApplication(applicationId, UpdateApplicationNameRequest(name))
    }

    override fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        updateApplication(applicationId, UpdateApplicationCompanyNameRequest(companyName))
    }

    override fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        updateApplication(applicationId, UpdateApplicationMailingAddressRequest(mailingAddress))
    }

    override fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        updateApplication(applicationId, UpdateApplicationPrivacyPolicyRequest(privacyPolicy))
    }

    override fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        updateApplication(applicationId, UpdateApplicationSupportContactRequest(supportContact))
    }

    override fun updateApplicationAppLink(applicationId: String, appLink: String) {
        updateApplication(applicationId, UpdateApplicationAppLinkRequest(appLink))
    }

    override fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        updateApplication(applicationId, UpdateApplicationEmailPreferencesRequest(EmailPreferencesRequest(
            senderEmail = emailPreferences.senderEmail,
            senderName = emailPreferences.senderName,
            primaryColour = emailPreferences.primaryColour,
            secondaryColour = emailPreferences.secondaryColour,
            onlySendEssentialEmails = emailPreferences.onlySendEssentialEmails,
            callToAction = emailPreferences.callToAction?.let {
                CallToActionRequest(
                    actionTarget = it.actionTarget,
                    headline = it.headline,
                    actionText = it.actionText
                )
            }
        )))
    }

    override fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        updateApplication(applicationId, UpdateApplicationLogoUrlRequest(logoUrl))
    }

    private fun updateApplication(applicationId: String, request: UpdateApplicationRequest) {
        httpClient.postEmpty(Paths.getUpdateApplicationPath(applicationId)) {
            addRequestHeaders()
            setBody(request)
        }
    }

    override fun deleteApplication(applicationId: String) {
        httpClient.deleteEmpty(Paths.getDeleteApplicationPath(applicationId))
    }

    override fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return httpClient.post(Paths.getLogoUploadUrlPath(applicationId)) {
            addRequestHeaders()
            setBody(GetLogoUploadUrlRequest(contentType))
        }
    }

    override fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        httpClient.postEmpty(Paths.getAddAuthKeyPath(applicationId)) {
            addRequestHeaders()
            setBody(key.toAddAuthKeyRequest())
        }
    }

    override fun addAuthIssuer(applicationId: String, url: String) {
        httpClient.postEmpty(Paths.getAddAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddAuthIssuerRequest(url))
        }
    }

    override fun deleteAuthIssuer(applicationId: String, url: String) {
        httpClient.deleteEmpty(Paths.getDeleteAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(DeleteAuthIssuerRequest(url))
        }
    }

    override fun addCorsDomain(applicationId: String, url: String) {
        httpClient.postEmpty(Paths.getAddCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(AddCorsDomainRequest(url))
        }
    }

    override fun removeCorsDomain(applicationId: String, url: String) {
        httpClient.deleteEmpty(Paths.getRemoveCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveCorsDomainRequest(url))
        }
    }

    override fun addApplicationOwner(applicationId: String, userId: String) {
        httpClient.postEmpty(Paths.getAddApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddApplicationOwnerRequest(userId))
        }
    }

    override fun removeApplicationOwner(applicationId: String, userId: String) {
        httpClient.deleteEmpty(Paths.getRemoveApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveApplicationOwnerRequest(userId))
        }
    }

    override fun getApplicationOwnersDetails(applicationId: String): Array<ApplicationOwnerDetailsResponse> {
        return httpClient.get(Paths.getApplicationOwnersDetailsPath(applicationId))
    }
}