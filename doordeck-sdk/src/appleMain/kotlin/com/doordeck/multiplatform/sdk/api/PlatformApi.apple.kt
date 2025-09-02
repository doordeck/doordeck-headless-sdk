package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.toBasicAuthKey
import com.doordeck.multiplatform.sdk.model.data.toBasicCreateApplication
import com.doordeck.multiplatform.sdk.model.data.toBasicEmailPreferences
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.toGetLogoUploadUrlResponse
import platform.Foundation.NSURL
import platform.Foundation.NSUUID

/**
 * Platform-specific implementations of platform-related API calls.
 */
actual object PlatformApi {
    /**
     * @see PlatformClient.createApplicationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun createApplication(application: PlatformOperations.CreateApplication): String {
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
    suspend fun getApplication(applicationId: NSUUID): ApplicationResponse {
        return PlatformClient.getApplicationRequest(applicationId.UUIDString)
            .toApplicationResponse()
    }

    /**
     * @see PlatformClient.updateApplicationNameRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationName(applicationId: NSUUID, name: String) {
        return PlatformClient.updateApplicationNameRequest(applicationId.UUIDString, name)
    }

    /**
     * @see PlatformClient.updateApplicationCompanyNameRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationCompanyName(applicationId: NSUUID, companyName: String) {
        return PlatformClient.updateApplicationCompanyNameRequest(applicationId.UUIDString, companyName)
    }

    /**
     * @see PlatformClient.updateApplicationMailingAddressRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationMailingAddress(applicationId: NSUUID, mailingAddress: String) {
        return PlatformClient.updateApplicationMailingAddressRequest(applicationId.UUIDString, mailingAddress)
    }

    /**
     * @see PlatformClient.updateApplicationPrivacyPolicyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationPrivacyPolicy(applicationId: NSUUID, privacyPolicy: String) {
        return PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId.UUIDString, privacyPolicy)
    }

    /**
     * @see PlatformClient.updateApplicationSupportContactRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationSupportContact(applicationId: NSUUID, supportContact: String) {
        return PlatformClient.updateApplicationSupportContactRequest(applicationId.UUIDString, supportContact)
    }

    /**
     * @see PlatformClient.updateApplicationAppLinkRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationAppLink(applicationId: NSUUID, appLink: String) {
        return PlatformClient.updateApplicationAppLinkRequest(applicationId.UUIDString, appLink)
    }

    /**
     * @see PlatformClient.updateApplicationEmailPreferencesRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationEmailPreferences(applicationId: NSUUID, emailPreferences: PlatformOperations.EmailPreferences) {
        return PlatformClient.updateApplicationEmailPreferencesRequest(applicationId.UUIDString, emailPreferences.toBasicEmailPreferences())
    }

    /**
     * @see PlatformClient.updateApplicationLogoUrlRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationLogoUrl(applicationId: NSUUID, logoUrl: String) {
        return PlatformClient.updateApplicationLogoUrlRequest(applicationId.UUIDString, logoUrl)
    }

    /**
     * @see PlatformClient.deleteApplicationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteApplication(applicationId: NSUUID) {
        return PlatformClient.deleteApplicationRequest(applicationId.UUIDString)
    }

    /**
     * @see PlatformClient.getLogoUploadUrlRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getLogoUploadUrl(applicationId: NSUUID, contentType: String): GetLogoUploadUrlResponse {
        return PlatformClient.getLogoUploadUrlRequest(applicationId.UUIDString, contentType)
            .toGetLogoUploadUrlResponse()
    }

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addAuthKey(applicationId: NSUUID, key: PlatformOperations.AuthKey) {
        return PlatformClient.addAuthKeyRequest(applicationId.UUIDString, key.toBasicAuthKey())
    }

    /**
     * @see PlatformClient.addAuthIssuerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addAuthIssuer(applicationId: NSUUID, url: NSURL) {
        return PlatformClient.addAuthIssuerRequest(applicationId.UUIDString, url.absoluteString ?: "")
    }

    /**
     * @see PlatformClient.deleteAuthIssuerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteAuthIssuer(applicationId: NSUUID, url: NSURL) {
        return PlatformClient.deleteAuthIssuerRequest(applicationId.UUIDString, url.absoluteString ?: "")
    }

    /**
     * @see PlatformClient.addCorsDomainRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addCorsDomain(applicationId: NSUUID, url: NSURL) {
        return PlatformClient.addCorsDomainRequest(applicationId.UUIDString, url.absoluteString ?: "")
    }

    /**
     * @see PlatformClient.removeCorsDomainRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun removeCorsDomain(applicationId: NSUUID, url: NSURL) {
        return PlatformClient.removeCorsDomainRequest(applicationId.UUIDString, url.absoluteString ?: "")
    }

    /**
     * @see PlatformClient.addApplicationOwnerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addApplicationOwner(applicationId: NSUUID, userId: NSUUID) {
        return PlatformClient.addApplicationOwnerRequest(applicationId.UUIDString, userId.UUIDString)
    }

    /**
     * @see PlatformClient.removeApplicationOwnerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun removeApplicationOwner(applicationId: NSUUID, userId: NSUUID) {
        return PlatformClient.removeApplicationOwnerRequest(applicationId.UUIDString, userId.UUIDString)
    }

    /**
     * @see PlatformClient.getApplicationOwnersDetailsRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getApplicationOwnersDetails(applicationId: NSUUID): List<ApplicationOwnerDetailsResponse> {
        return PlatformClient.getApplicationOwnersDetailsRequest(applicationId.UUIDString)
            .toApplicationOwnerDetailsResponse()
    }
}

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
actual fun platform(): PlatformApi = PlatformApi