package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.model.data.BasicAuthKey
import com.doordeck.multiplatform.sdk.model.data.BasicCreateApplication
import com.doordeck.multiplatform.sdk.model.data.BasicEmailPreferences
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.requests.AddApplicationOwnerRequest
import com.doordeck.multiplatform.sdk.model.requests.AddAuthIssuerRequest
import com.doordeck.multiplatform.sdk.model.requests.AddCorsDomainRequest
import com.doordeck.multiplatform.sdk.model.requests.CallToActionRequest
import com.doordeck.multiplatform.sdk.model.requests.DeleteAuthIssuerRequest
import com.doordeck.multiplatform.sdk.model.requests.EmailPreferencesRequest
import com.doordeck.multiplatform.sdk.model.requests.GetLogoUploadUrlRequest
import com.doordeck.multiplatform.sdk.model.requests.RemoveApplicationOwnerRequest
import com.doordeck.multiplatform.sdk.model.requests.RemoveCorsDomainRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationAppLinkRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationCompanyNameRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationEmailPreferencesRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationLogoUrlRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationMailingAddressRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationNameRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationPrivacyPolicyRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateApplicationSupportContactRequest
import com.doordeck.multiplatform.sdk.model.requests.toAddAuthKeyRequest
import com.doordeck.multiplatform.sdk.model.requests.toCreateApplicationRequest
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

/**
 * Internal implementation of the platform API client.
 * Handles all  requests related to platform.
 */
internal object PlatformClient {

    /**
     * Creates a new application; Applications are used to divide users between third-parties within Doordeck
     * and define branding, UI, and authentication elements.
     *
     * @param application Contains new application definition to be created.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun createApplicationRequest(application: BasicCreateApplication) {
        CloudHttpClient.client.post(Paths.getCreateApplicationPath()) {
            addRequestHeaders()
            setBody(application.toCreateApplicationRequest())
        }
    }

    /**
     * Lists all application's owned by the current user.
     *
     * @return List of [ApplicationResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    suspend fun listApplicationsRequest(): List<ApplicationResponse> {
        return CloudHttpClient.client.get(Paths.getListApplicationsPath()).body()
    }

    /**
     * Retrieves the application by its application ID.
     *
     * @param applicationId The application's unique identifier.
     * @return [ApplicationResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getApplicationRequest(applicationId: String): ApplicationResponse {
        return CloudHttpClient.client.get(Paths.getApplicationPath(applicationId)).body()
    }

    /**
     * Updates the display name of an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param name The new display name.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationNameRequest(applicationId: String, name: String) {
        updateApplication(applicationId, UpdateApplicationNameRequest(name))
    }

    /**
     * Updates the company name of an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param companyName The new company name.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationCompanyNameRequest(applicationId: String, companyName: String) {
        updateApplication(applicationId, UpdateApplicationCompanyNameRequest(companyName))
    }

    /**
     * Updates the company's registered address of an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param mailingAddress The new mailing address.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationMailingAddressRequest(applicationId: String, mailingAddress: String) {
        updateApplication(applicationId, UpdateApplicationMailingAddressRequest(mailingAddress))
    }

    /**
     * Updates the privacy policy URL from an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param privacyPolicy The URL from the new privacy policy, must start with https.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationPrivacyPolicyRequest(applicationId: String, privacyPolicy: String) {
        updateApplication(applicationId, UpdateApplicationPrivacyPolicyRequest(privacyPolicy))
    }

    /**
     * Updates the support contact URL from an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param supportContact The URL from the new support contact, must start with https or mailto.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationSupportContactRequest(applicationId: String, supportContact: String) {
        updateApplication(applicationId, UpdateApplicationSupportContactRequest(supportContact))
    }

    /**
     * Updates the deep link URL from an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param appLink The URL from the new application deep link.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationAppLinkRequest(applicationId: String, appLink: String) {
        updateApplication(applicationId, UpdateApplicationAppLinkRequest(appLink))
    }

    /**
     * Updates the email preferences of an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param emailPreferences Contains new email preferences configuration to be updated.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationEmailPreferencesRequest(
        applicationId: String,
        emailPreferences: BasicEmailPreferences
    ) {
        updateApplication(
            applicationId = applicationId,
            request = UpdateApplicationEmailPreferencesRequest(
                EmailPreferencesRequest(
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
                )
            )
        )
    }

    /**
     * Updates the application's logo URL with a new image.
     *
     * @param applicationId The application's unique identifier.
     * @param logoUrl The new logo URL, must be hosted on https or cdn.doordeck.com.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationLogoUrlRequest(applicationId: String, logoUrl: String) {
        updateApplication(applicationId, UpdateApplicationLogoUrlRequest(logoUrl))
    }

    /**
     * Handles the request to update an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param request The specific [UpdateApplicationRequest] request to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    private suspend fun updateApplication(applicationId: String, request: UpdateApplicationRequest) {
        CloudHttpClient.client.post(Paths.getUpdateApplicationPath(applicationId)) {
            addRequestHeaders()
            setBody(request)
        }
    }

    /**
     * Permanently deletes an application and all associated resources.
     *
     * @param applicationId The application's unique identifier.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun deleteApplicationRequest(applicationId: String) {
        CloudHttpClient.client.delete(Paths.getDeleteApplicationPath(applicationId))
    }

    /**
     * Generates a pre-signed URL ready for uploading the application logo to.
     *
     * @param applicationId The application's unique identifier.
     * @param contentType Content-type of the logo (image/png or image/jpeg).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getLogoUploadUrlRequest(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return CloudHttpClient.client.post(Paths.getLogoUploadUrlPath(applicationId)) {
            addRequestHeaders()
            setBody(GetLogoUploadUrlRequest(contentType))
        }.body()
    }

    /**
     * Adds a permitted authentication key (RSA/EC/Ed25519) for application users.
     *
     * @param applicationId The application's unique identifier.
     * @param key Contains new key definition to be added.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addAuthKeyRequest(applicationId: String, key: BasicAuthKey) {
        CloudHttpClient.client.post(Paths.getAddAuthKeyPath(applicationId)) {
            addRequestHeaders()
            setBody(key.toAddAuthKeyRequest())
        }
    }

    /**
     * Registers a unique authentication issuer URL for third-party applications.
     * The issuer must:
     * - Be a globally unique URL controlled by your application (e.g., "https://myapp.com")
     * - Exactly match the `iss` field in all your application's auth tokens
     *
     * @param applicationId The application's unique identifier.
     * @param url The url issuer.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addAuthIssuerRequest(applicationId: String, url: String) {
        CloudHttpClient.client.post(Paths.getAddAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddAuthIssuerRequest(url))
        }
    }

    /**
     * Deletes an auth issuer.
     *
     * @param applicationId The application's unique identifier.
     * @param url The url issuer.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    suspend fun deleteAuthIssuerRequest(applicationId: String, url: String) {
        CloudHttpClient.client.delete(Paths.getDeleteAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(DeleteAuthIssuerRequest(url))
        }
    }

    /**
     * Adds a new CORS domain to the allowed origins for an application.
     *
     * @param applicationId The application's unique identifier.
     * @param url The CORS domain to be added.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addCorsDomainRequest(applicationId: String, url: String) {
        CloudHttpClient.client.post(Paths.getAddCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(AddCorsDomainRequest(url))
        }
    }

    /**
     * Removes a CORS domain from the allowed origins for an application.
     *
     * @param applicationId The application's unique identifier.
     * @param url The CORS domain to be removed.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    suspend fun removeCorsDomainRequest(applicationId: String, url: String) {
        CloudHttpClient.client.delete(Paths.getRemoveCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveCorsDomainRequest(url))
        }
    }

    /**
     * Adds a new owner to an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param userId The user's unique identifier.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addApplicationOwnerRequest(applicationId: String, userId: String) {
        CloudHttpClient.client.post(Paths.getAddApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddApplicationOwnerRequest(userId))
        }
    }

    /**
     * Removes an owner from an existing application.
     *
     * @param applicationId The application's unique identifier.
     * @param userId The user's unique identifier.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    suspend fun removeApplicationOwnerRequest(applicationId: String, userId: String) {
        CloudHttpClient.client.delete(Paths.getRemoveApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveApplicationOwnerRequest(userId))
        }
    }

    /**
     * Retrieves the details of all owners of an application, the requesting user should be the application owner.
     *
     * @param applicationId The application's unique identifier.
     * @return List of [ApplicationOwnerDetailsResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getApplicationOwnersDetailsRequest(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return CloudHttpClient.client.get(Paths.getApplicationOwnersDetailsPath(applicationId)).body()
    }
}