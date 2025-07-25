package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.Platform
import com.doordeck.multiplatform.sdk.model.data.toBasicAuthKey
import com.doordeck.multiplatform.sdk.model.data.toBasicCreateApplication
import com.doordeck.multiplatform.sdk.model.data.toBasicEmailPreferences
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.toGetLogoUploadUrlResponse

/**
 * Platform-specific implementations of platform-related API calls.
 */
actual object PlatformApi {
    /**
     * @see PlatformClient.createApplicationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun createApplication(application: Platform.CreateApplication) {
        return PlatformClient.createApplicationRequest(application.toBasicCreateApplication())
    }

    /**
     * @see PlatformClient.listApplicationsRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun listApplications(): List<ApplicationResponse> {
        return PlatformClient.listApplicationsRequest()
            .toApplicationResponse()
    }

    /**
     * @see PlatformClient.getApplicationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getApplication(applicationId: String): ApplicationResponse {
        return PlatformClient.getApplicationRequest(applicationId)
            .toApplicationResponse()
    }

    /**
     * @see PlatformClient.updateApplicationNameRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationName(applicationId: String, name: String) {
        return PlatformClient.updateApplicationNameRequest(applicationId, name)
    }

    /**
     * @see PlatformClient.updateApplicationCompanyNameRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName)
    }

    /**
     * @see PlatformClient.updateApplicationMailingAddressRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress)
    }

    /**
     * @see PlatformClient.updateApplicationPrivacyPolicyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy)
    }

    /**
     * @see PlatformClient.updateApplicationSupportContactRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact)
    }

    /**
     * @see PlatformClient.updateApplicationAppLinkRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink)
    }

    /**
     * @see PlatformClient.updateApplicationEmailPreferencesRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences.toBasicEmailPreferences())
    }

    /**
     * @see PlatformClient.updateApplicationLogoUrlRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl)
    }

    /**
     * @see PlatformClient.deleteApplicationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteApplication(applicationId: String) {
        return PlatformClient.deleteApplicationRequest(applicationId)
    }

    /**
     * @see PlatformClient.getLogoUploadUrlRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return PlatformClient.getLogoUploadUrlRequest(applicationId, contentType)
            .toGetLogoUploadUrlResponse()
    }

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return PlatformClient.addAuthKeyRequest(applicationId, key.toBasicAuthKey())
    }

    /**
     * @see PlatformClient.addAuthIssuerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.addAuthIssuerRequest(applicationId, url)
    }

    /**
     * @see PlatformClient.deleteAuthIssuerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.deleteAuthIssuerRequest(applicationId, url)
    }

    /**
     * @see PlatformClient.addCorsDomainRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addCorsDomain(applicationId: String, url: String) {
        return PlatformClient.addCorsDomainRequest(applicationId, url)
    }

    /**
     * @see PlatformClient.removeCorsDomainRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun removeCorsDomain(applicationId: String, url: String) {
        return PlatformClient.removeCorsDomainRequest(applicationId, url)
    }

    /**
     * @see PlatformClient.addApplicationOwnerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.addApplicationOwnerRequest(applicationId, userId)
    }

    /**
     * @see PlatformClient.removeApplicationOwnerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun removeApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.removeApplicationOwnerRequest(applicationId, userId)
    }

    /**
     * @see PlatformClient.getApplicationOwnersDetailsRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return PlatformClient.getApplicationOwnersDetailsRequest(applicationId)
            .toApplicationOwnerDetailsResponse()
    }
}

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
actual fun platform(): PlatformApi = PlatformApi