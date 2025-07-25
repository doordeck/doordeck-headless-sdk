package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.BasicPlatform
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of platform-related API calls.
 */
actual object PlatformApi {
    /**
     * @see PlatformClient.createApplicationRequest
     */
    @DoordeckOnly
    suspend fun createApplication(application: BasicPlatform.BasicCreateApplication) {
        return PlatformClient.createApplicationRequest(application)
    }

    /**
     * Async variant of [PlatformApi.createApplication] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun createApplicationAsync(application: BasicPlatform.BasicCreateApplication): CompletableFuture<Unit> {
        return completableFuture { createApplication(application) }
    }

    /**
     * @see PlatformClient.listApplicationsRequest
     */
    @DoordeckOnly
    suspend fun listApplications(): List<ApplicationResponse> {
        return PlatformClient.listApplicationsRequest()
    }

    /**
     * Async variant of [PlatformApi.listApplications] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun listApplicationsAsync(): CompletableFuture<List<ApplicationResponse>> {
        return completableFuture { listApplications() }
    }

    /**
     * @see PlatformClient.getApplicationRequest
     */
    @DoordeckOnly
    suspend fun getApplication(applicationId: String): ApplicationResponse {
        return PlatformClient.getApplicationRequest(applicationId)
    }

    /**
     * Async variant of [PlatformApi.getApplication] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getApplicationAsync(applicationId: String): CompletableFuture<ApplicationResponse> {
        return completableFuture { getApplication(applicationId) }
    }

    /**
     * @see PlatformClient.updateApplicationNameRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationName(applicationId: String, name: String) {
        return PlatformClient.updateApplicationNameRequest(applicationId, name)
    }

    /**
     * Async variant of [PlatformApi.updateApplicationName] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationNameAsync(applicationId: String, name: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationName(applicationId, name) }
    }

    /**
     * @see PlatformClient.updateApplicationCompanyNameRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName)
    }

    /**
     * Async variant of [PlatformApi.updateApplicationCompanyName] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationCompanyNameAsync(applicationId: String, companyName: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationCompanyName(applicationId, companyName) }
    }

    /**
     * @see PlatformClient.updateApplicationMailingAddressRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress)
    }

    /**
     * Async variant of [PlatformApi.updateApplicationMailingAddress] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationMailingAddressAsync(applicationId: String, mailingAddress: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationMailingAddress(applicationId, mailingAddress) }
    }

    /**
     * @see PlatformClient.updateApplicationPrivacyPolicyRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy)
    }

    /**
     * Async variant of [PlatformApi.updateApplicationPrivacyPolicy] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationPrivacyPolicyAsync(applicationId: String, privacyPolicy: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationPrivacyPolicy(applicationId, privacyPolicy) }
    }

    /**
     * @see PlatformClient.updateApplicationSupportContactRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact)
    }

    /**
     * Async variant of [PlatformApi.updateApplicationSupportContact] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationSupportContactAsync(applicationId: String, supportContact: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationSupportContact(applicationId, supportContact) }
    }

    /**
     * @see PlatformClient.updateApplicationAppLinkRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink)
    }

    /**
     * Async variant of [PlatformApi.updateApplicationAppLink] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationAppLinkAsync(applicationId: String, appLink: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationAppLink(applicationId, appLink) }
    }

    /**
     * @see PlatformClient.updateApplicationEmailPreferencesRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: BasicPlatform.BasicEmailPreferences) {
        return PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences)
    }

    /**
     * Async variant of [PlatformApi.updateApplicationEmailPreferences] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationEmailPreferencesAsync(applicationId: String, emailPreferences: BasicPlatform.BasicEmailPreferences): CompletableFuture<Unit> {
        return completableFuture { updateApplicationEmailPreferences(applicationId, emailPreferences) }
    }

    /**
     * @see PlatformClient.updateApplicationLogoUrlRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl)
    }

    /**
     * Async variant of [PlatformApi.updateApplicationLogoUrl] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationLogoUrlAsync(applicationId: String, logoUrl: String): CompletableFuture<Unit> {
        return completableFuture { updateApplicationLogoUrl(applicationId, logoUrl) }
    }

    /**
     * @see PlatformClient.deleteApplicationRequest
     */
    @DoordeckOnly
    suspend fun deleteApplication(applicationId: String) {
        return PlatformClient.deleteApplicationRequest(applicationId)
    }

    /**
     * Async variant of [PlatformApi.deleteApplication] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun deleteApplicationAsync(applicationId: String): CompletableFuture<Unit> {
        return completableFuture { deleteApplication(applicationId) }
    }

    /**
     * @see PlatformClient.getLogoUploadUrlRequest
     */
    @DoordeckOnly
    suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return PlatformClient.getLogoUploadUrlRequest(applicationId, contentType)
    }

    /**
     * Async variant of [PlatformApi.getLogoUploadUrl] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getLogoUploadUrlAsync(applicationId: String, contentType: String): CompletableFuture<GetLogoUploadUrlResponse> {
        return completableFuture { getLogoUploadUrl(applicationId, contentType) }
    }

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    suspend fun addAuthKey(applicationId: String, key: BasicPlatform.BasicAuthKey) {
        return PlatformClient.addAuthKeyRequest(applicationId, key)
    }

    /**
     * Async variant of [PlatformApi.addAuthKey] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun addAuthKeyAsync(applicationId: String, key: BasicPlatform.BasicAuthKey): CompletableFuture<Unit> {
        return completableFuture { addAuthKey(applicationId, key) }
    }

    /**
     * @see PlatformClient.addAuthIssuerRequest
     */
    @DoordeckOnly
    suspend fun addAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.addAuthIssuerRequest(applicationId, url)
    }

    /**
     * Async variant of [PlatformApi.addAuthIssuer] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun addAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return completableFuture { addAuthIssuer(applicationId, url) }
    }

    /**
     * @see PlatformClient.deleteAuthIssuerRequest
     */
    @DoordeckOnly
    suspend fun deleteAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.deleteAuthIssuerRequest(applicationId, url)
    }

    /**
     * Async variant of [PlatformApi.deleteAuthIssuer] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun deleteAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return completableFuture { deleteAuthIssuer(applicationId, url) }
    }

    /**
     * @see PlatformClient.addCorsDomainRequest
     */
    @DoordeckOnly
    suspend fun addCorsDomain(applicationId: String, url: String) {
        return PlatformClient.addCorsDomainRequest(applicationId, url)
    }

    /**
     * Async variant of [PlatformApi.addCorsDomain] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun addCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return completableFuture { addCorsDomain(applicationId, url) }
    }

    /**
     * @see PlatformClient.removeCorsDomainRequest
     */
    @DoordeckOnly
    suspend fun removeCorsDomain(applicationId: String, url: String) {
        return PlatformClient.removeCorsDomainRequest(applicationId, url)
    }

    /**
     * Async variant of [PlatformApi.removeCorsDomain] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun removeCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return completableFuture { removeCorsDomain(applicationId, url) }
    }

    /**
     * @see PlatformClient.addApplicationOwnerRequest
     */
    @DoordeckOnly
    suspend fun addApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.addApplicationOwnerRequest(applicationId, userId)
    }

    /**
     * Async variant of [PlatformApi.addApplicationOwner] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun addApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit> {
        return completableFuture { addApplicationOwner(applicationId, userId) }
    }

    /**
     * @see PlatformClient.removeApplicationOwnerRequest
     */
    @DoordeckOnly
    suspend fun removeApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.removeApplicationOwnerRequest(applicationId, userId)
    }

    /**
     * Async variant of [PlatformApi.removeApplicationOwner] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun removeApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit> {
        return completableFuture { removeApplicationOwner(applicationId, userId) }
    }

    /**
     * @see PlatformClient.getApplicationOwnersDetailsRequest
     */
    @DoordeckOnly
    suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return PlatformClient.getApplicationOwnersDetailsRequest(applicationId)
    }

    /**
     * Async variant of [PlatformApi.getApplicationOwnersDetails] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getApplicationOwnersDetailsAsync(applicationId: String): CompletableFuture<List<ApplicationOwnerDetailsResponse>> {
        return completableFuture { getApplicationOwnersDetails(applicationId) }
    }
}

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
actual fun platform(): PlatformApi = PlatformApi