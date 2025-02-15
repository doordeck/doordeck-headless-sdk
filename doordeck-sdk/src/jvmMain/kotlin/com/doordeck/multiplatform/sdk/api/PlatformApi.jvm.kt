package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.Platform
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

actual object PlatformApi {
    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun createApplication(application: Platform.CreateApplication) {
        return PlatformClient.createApplicationRequest(application)
    }

    @DoordeckOnly
    fun createApplicationAsync(application: Platform.CreateApplication): CompletableFuture<Unit> {
        return completableFuture { createApplication(application) }
    }

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    suspend fun listApplications(): List<ApplicationResponse> {
        return PlatformClient.listApplicationsRequest()
    }

    @DoordeckOnly
    fun listApplicationsAsync(): CompletableFuture<List<ApplicationResponse>> {
        return completableFuture { listApplications() }
    }

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getApplication(applicationId: String): ApplicationResponse {
        return PlatformClient.getApplicationRequest(applicationId)
    }

    @DoordeckOnly
    fun getApplicationAsync(applicationId: String): CompletableFuture<ApplicationResponse> {
        return completableFuture { getApplication(applicationId) }
    }

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationName(applicationId: String, name: String) {
        return PlatformClient.updateApplicationNameRequest(applicationId, name)
    }

    @DoordeckOnly
    fun updateApplicationNameAsync(applicationId: String, name: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationName(applicationId, name) }
    }

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName)
    }

    @DoordeckOnly
    fun updateApplicationCompanyNameAsync(applicationId: String, companyName: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationCompanyName(applicationId, companyName) }
    }

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress)
    }

    @DoordeckOnly
    fun updateApplicationMailingAddressAsync(applicationId: String, mailingAddress: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationMailingAddress(applicationId, mailingAddress) }
    }

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy)
    }

    @DoordeckOnly
    fun updateApplicationPrivacyPolicyAsync(applicationId: String, privacyPolicy: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationPrivacyPolicy(applicationId, privacyPolicy) }
    }

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact)
    }

    @DoordeckOnly
    fun updateApplicationSupportContactAsync(applicationId: String, supportContact: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationSupportContact(applicationId, supportContact) }
    }

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink)
    }

    @DoordeckOnly
    fun updateApplicationAppLinkAsync(applicationId: String, appLink: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationAppLink(applicationId, appLink) }
    }

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences)
    }

    @DoordeckOnly
    fun updateApplicationEmailPreferencesAsync(applicationId: String, emailPreferences: Platform.EmailPreferences): CompletableFuture<Unit> {
        return completableFuture { updateApplicationEmailPreferences(applicationId, emailPreferences) }
    }

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl)
    }

    @DoordeckOnly
    fun updateApplicationLogoUrlAsync(applicationId: String, logoUrl: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationLogoUrl(applicationId, logoUrl) }
    }

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun deleteApplication(applicationId: String) {
        return PlatformClient.deleteApplicationRequest(applicationId)
    }

    @DoordeckOnly
    fun deleteApplicationAsync(applicationId: String): CompletableFuture<Unit> {
        return completableFuture { deleteApplication(applicationId) }
    }

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return PlatformClient.getLogoUploadUrlRequest(applicationId, contentType)
    }

    @DoordeckOnly
    fun getLogoUploadUrlAsync(applicationId: String, contentType: String): CompletableFuture<GetLogoUploadUrlResponse> {
        return completableFuture { getLogoUploadUrl(applicationId, contentType) }
    }

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return PlatformClient.addAuthKeyRequest(applicationId, key)
    }

    @DoordeckOnly
    fun addAuthKeyAsync(applicationId: String, key: Platform.AuthKey): CompletableFuture<Unit> {
        return completableFuture { addAuthKey(applicationId, key) }
    }

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.addAuthIssuerRequest(applicationId, url)
    }

    @DoordeckOnly
    fun addAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return completableFuture { addAuthIssuer(applicationId, url) }
    }

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    suspend fun deleteAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.deleteAuthIssuerRequest(applicationId, url)
    }

    @DoordeckOnly
    fun deleteAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return completableFuture { deleteAuthIssuer(applicationId, url) }
    }

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addCorsDomain(applicationId: String, url: String) {
        return PlatformClient.addCorsDomainRequest(applicationId, url)
    }

    @DoordeckOnly
    fun addCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return completableFuture { addCorsDomain(applicationId, url) }
    }

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    suspend fun removeCorsDomain(applicationId: String, url: String) {
        return PlatformClient.removeCorsDomainRequest(applicationId, url)
    }

    @DoordeckOnly
    fun removeCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return completableFuture { removeCorsDomain(applicationId, url) }
    }

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.addApplicationOwnerRequest(applicationId, userId)
    }

    @DoordeckOnly
    fun addApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit> {
        return completableFuture { addApplicationOwner(applicationId, userId) }
    }

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    suspend fun removeApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.removeApplicationOwnerRequest(applicationId, userId)
    }

    @DoordeckOnly
    fun removeApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit> {
        return completableFuture { removeApplicationOwner(applicationId, userId) }
    }

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return PlatformClient.getApplicationOwnersDetailsRequest(applicationId)
    }

    @DoordeckOnly
    fun getApplicationOwnersDetailsAsync(applicationId: String): CompletableFuture<List<ApplicationOwnerDetailsResponse>> {
        return completableFuture { getApplicationOwnersDetails(applicationId) }
    }
}

actual fun platform(): PlatformApi = PlatformApi