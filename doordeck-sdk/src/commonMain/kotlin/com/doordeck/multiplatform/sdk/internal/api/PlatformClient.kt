package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.requests.AddApplicationOwnerRequest
import com.doordeck.multiplatform.sdk.api.requests.AddAuthIssuerRequest
import com.doordeck.multiplatform.sdk.api.requests.AddCorsDomainRequest
import com.doordeck.multiplatform.sdk.api.requests.CallToActionRequest
import com.doordeck.multiplatform.sdk.api.requests.DeleteAuthIssuerRequest
import com.doordeck.multiplatform.sdk.api.requests.EmailPreferencesRequest
import com.doordeck.multiplatform.sdk.api.requests.GetLogoUploadUrlRequest
import com.doordeck.multiplatform.sdk.api.requests.RemoveApplicationOwnerRequest
import com.doordeck.multiplatform.sdk.api.requests.RemoveCorsDomainRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationAppLinkRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationCompanyNameRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationEmailPreferencesRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationLogoUrlRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationMailingAddressRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationNameRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationPrivacyPolicyRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationSupportContactRequest
import com.doordeck.multiplatform.sdk.api.requests.toAddAuthKeyRequest
import com.doordeck.multiplatform.sdk.api.requests.toCreateApplicationRequest
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.request.setBody

internal object PlatformClient : AbstractResourceImpl() {

    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun createApplicationRequest(application: Platform.CreateApplication) {
        CloudHttpClient.client.post<Unit>(Paths.getCreateApplicationPath()) {
            addRequestHeaders()
            setBody(application.toCreateApplicationRequest())
        }
    }

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    suspend fun listApplicationsRequest(): List<ApplicationResponse> {
        return CloudHttpClient.client.get(Paths.getListApplicationsPath())
    }

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getApplicationRequest(applicationId: String): ApplicationResponse {
        return CloudHttpClient.client.get(Paths.getApplicationPath(applicationId))
    }

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationNameRequest(applicationId: String, name: String) {
        updateApplication(applicationId, UpdateApplicationNameRequest(name))
    }

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationCompanyNameRequest(applicationId: String, companyName: String) {
        updateApplication(applicationId, UpdateApplicationCompanyNameRequest(companyName))
    }

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationMailingAddressRequest(applicationId: String, mailingAddress: String) {
        updateApplication(applicationId, UpdateApplicationMailingAddressRequest(mailingAddress))
    }

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationPrivacyPolicyRequest(applicationId: String, privacyPolicy: String) {
        updateApplication(applicationId, UpdateApplicationPrivacyPolicyRequest(privacyPolicy))
    }

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationSupportContactRequest(applicationId: String, supportContact: String) {
        updateApplication(applicationId, UpdateApplicationSupportContactRequest(supportContact))
    }

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationAppLinkRequest(applicationId: String, appLink: String) {
        updateApplication(applicationId, UpdateApplicationAppLinkRequest(appLink))
    }

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationEmailPreferencesRequest(applicationId: String, emailPreferences: Platform.EmailPreferences) {
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

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationLogoUrlRequest(applicationId: String, logoUrl: String) {
        updateApplication(applicationId, UpdateApplicationLogoUrlRequest(logoUrl))
    }

    private suspend fun updateApplication(applicationId: String, request: UpdateApplicationRequest) {
        CloudHttpClient.client.post<Unit>(Paths.getUpdateApplicationPath(applicationId)) {
            addRequestHeaders()
            setBody(request)
        }
    }

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun deleteApplicationRequest(applicationId: String) {
        CloudHttpClient.client.delete<Unit>(Paths.getDeleteApplicationPath(applicationId))
    }

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getLogoUploadUrlRequest(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return CloudHttpClient.client.post(Paths.getLogoUploadUrlPath(applicationId)) {
            addRequestHeaders()
            setBody(GetLogoUploadUrlRequest(contentType))
        }
    }

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addAuthKeyRequest(applicationId: String, key: Platform.AuthKey) {
        CloudHttpClient.client.post<Unit>(Paths.getAddAuthKeyPath(applicationId)) {
            addRequestHeaders()
            setBody(key.toAddAuthKeyRequest())
        }
    }

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addAuthIssuerRequest(applicationId: String, url: String) {
        CloudHttpClient.client.post<Unit>(Paths.getAddAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddAuthIssuerRequest(url))
        }
    }

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    suspend fun deleteAuthIssuerRequest(applicationId: String, url: String) {
        CloudHttpClient.client.delete<Unit>(Paths.getDeleteAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(DeleteAuthIssuerRequest(url))
        }
    }

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addCorsDomainRequest(applicationId: String, url: String) {
        CloudHttpClient.client.post<Unit>(Paths.getAddCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(AddCorsDomainRequest(url))
        }
    }

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    suspend fun removeCorsDomainRequest(applicationId: String, url: String) {
        CloudHttpClient.client.delete<Unit>(Paths.getRemoveCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveCorsDomainRequest(url))
        }
    }

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addApplicationOwnerRequest(applicationId: String, userId: String) {
        CloudHttpClient.client.post<Unit>(Paths.getAddApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddApplicationOwnerRequest(userId))
        }
    }

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    suspend fun removeApplicationOwnerRequest(applicationId: String, userId: String) {
        CloudHttpClient.client.delete<Unit>(Paths.getRemoveApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveApplicationOwnerRequest(userId))
        }
    }

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getApplicationOwnersDetailsRequest(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return CloudHttpClient.client.get(Paths.getApplicationOwnersDetailsPath(applicationId))
    }
}