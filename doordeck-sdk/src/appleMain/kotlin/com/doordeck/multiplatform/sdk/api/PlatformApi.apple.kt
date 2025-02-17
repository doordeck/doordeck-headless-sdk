package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.Platform
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse

actual object PlatformApi {
    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun createApplication(application: Platform.CreateApplication) {
        return PlatformClient.createApplicationRequest(application)
    }

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun listApplications(): List<ApplicationResponse> {
        return PlatformClient.listApplicationsRequest()
    }

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getApplication(applicationId: String): ApplicationResponse {
        return PlatformClient.getApplicationRequest(applicationId)
    }

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationName(applicationId: String, name: String) {
        return PlatformClient.updateApplicationNameRequest(applicationId, name)
    }

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName)
    }

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress)
    }

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy)
    }

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact)
    }

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink)
    }

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences)
    }

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl)
    }

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteApplication(applicationId: String) {
        return PlatformClient.deleteApplicationRequest(applicationId)
    }

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return PlatformClient.getLogoUploadUrlRequest(applicationId, contentType)
    }

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return PlatformClient.addAuthKeyRequest(applicationId, key)
    }

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.addAuthIssuerRequest(applicationId, url)
    }

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.deleteAuthIssuerRequest(applicationId, url)
    }

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addCorsDomain(applicationId: String, url: String) {
        return PlatformClient.addCorsDomainRequest(applicationId, url)
    }

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun removeCorsDomain(applicationId: String, url: String) {
        return PlatformClient.removeCorsDomainRequest(applicationId, url)
    }

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.addApplicationOwnerRequest(applicationId, userId)
    }

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun removeApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.removeApplicationOwnerRequest(applicationId, userId)
    }

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return PlatformClient.getApplicationOwnersDetailsRequest(applicationId)
    }
}

actual fun platform(): PlatformApi = PlatformApi