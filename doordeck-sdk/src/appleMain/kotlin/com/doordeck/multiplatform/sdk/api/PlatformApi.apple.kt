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
import com.doordeck.multiplatform.sdk.util.toNsUuid
import com.doordeck.multiplatform.sdk.util.toUrlString
import platform.Foundation.NSURLComponents
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
    suspend fun createApplication(application: PlatformOperations.CreateApplication): NSUUID = PlatformClient
        .createApplicationRequest(application.toBasicCreateApplication())
        .toNsUuid()

    /**
     * @see PlatformClient.listApplicationsRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun listApplications(): List<ApplicationResponse> = PlatformClient
        .listApplicationsRequest()
        .toApplicationResponse()

    /**
     * @see PlatformClient.getApplicationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getApplication(applicationId: NSUUID): ApplicationResponse = PlatformClient
        .getApplicationRequest(applicationId.UUIDString)
        .toApplicationResponse()

    /**
     * @see PlatformClient.updateApplicationNameRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationName(applicationId: NSUUID, name: String) = PlatformClient
        .updateApplicationNameRequest(
            applicationId = applicationId.UUIDString,
            name = name
        )

    /**
     * @see PlatformClient.updateApplicationCompanyNameRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationCompanyName(applicationId: NSUUID, companyName: String) = PlatformClient
        .updateApplicationCompanyNameRequest(
            applicationId = applicationId.UUIDString,
            companyName = companyName
        )

    /**
     * @see PlatformClient.updateApplicationMailingAddressRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationMailingAddress(applicationId: NSUUID, mailingAddress: String) = PlatformClient
        .updateApplicationMailingAddressRequest(
            applicationId = applicationId.UUIDString,
            mailingAddress = mailingAddress
        )

    /**
     * @see PlatformClient.updateApplicationPrivacyPolicyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationPrivacyPolicy(applicationId: NSUUID, privacyPolicy: NSURLComponents) = PlatformClient
        .updateApplicationPrivacyPolicyRequest(
            applicationId = applicationId.UUIDString,
            privacyPolicy = privacyPolicy.toUrlString()
        )

    /**
     * @see PlatformClient.updateApplicationSupportContactRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationSupportContact(applicationId: NSUUID, supportContact: NSURLComponents) = PlatformClient
        .updateApplicationSupportContactRequest(
            applicationId = applicationId.UUIDString,
            supportContact = supportContact.toUrlString()
        )

    /**
     * @see PlatformClient.updateApplicationAppLinkRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationAppLink(applicationId: NSUUID, appLink: NSURLComponents) = PlatformClient
        .updateApplicationAppLinkRequest(
            applicationId = applicationId.UUIDString,
            appLink = appLink.toUrlString()
        )

    /**
     * @see PlatformClient.updateApplicationEmailPreferencesRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationEmailPreferences(
        applicationId: NSUUID,
        emailPreferences: PlatformOperations.EmailPreferences
    ) = PlatformClient
        .updateApplicationEmailPreferencesRequest(
            applicationId = applicationId.UUIDString,
            emailPreferences = emailPreferences.toBasicEmailPreferences()
        )

    /**
     * @see PlatformClient.updateApplicationLogoUrlRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun updateApplicationLogoUrl(applicationId: NSUUID, logoUrl: NSURLComponents) = PlatformClient
        .updateApplicationLogoUrlRequest(
            applicationId = applicationId.UUIDString,
            logoUrl = logoUrl.toUrlString()
        )

    /**
     * @see PlatformClient.deleteApplicationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteApplication(applicationId: NSUUID) = PlatformClient
        .deleteApplicationRequest(applicationId.UUIDString)

    /**
     * @see PlatformClient.getLogoUploadUrlRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getLogoUploadUrl(
        applicationId: NSUUID,
        contentType: String
    ): GetLogoUploadUrlResponse = PlatformClient
        .getLogoUploadUrlRequest(
            applicationId = applicationId.UUIDString,
            contentType = contentType
        )
        .toGetLogoUploadUrlResponse()

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addAuthKey(applicationId: NSUUID, key: PlatformOperations.AuthKey) = PlatformClient
        .addAuthKeyRequest(
            applicationId = applicationId.UUIDString,
            key = key.toBasicAuthKey()
        )

    /**
     * @see PlatformClient.addAuthIssuerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addAuthIssuer(applicationId: NSUUID, url: NSURLComponents) = PlatformClient
        .addAuthIssuerRequest(
            applicationId = applicationId.UUIDString,
            url = url.toUrlString()
        )

    /**
     * @see PlatformClient.deleteAuthIssuerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteAuthIssuer(applicationId: NSUUID, url: NSURLComponents) = PlatformClient
        .deleteAuthIssuerRequest(
            applicationId = applicationId.UUIDString,
            url = url.toUrlString()
        )

    /**
     * @see PlatformClient.addCorsDomainRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addCorsDomain(applicationId: NSUUID, url: NSURLComponents) = PlatformClient
        .addCorsDomainRequest(
            applicationId = applicationId.UUIDString,
            url = url.toUrlString()
        )

    /**
     * @see PlatformClient.removeCorsDomainRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun removeCorsDomain(applicationId: NSUUID, url: NSURLComponents) = PlatformClient
        .removeCorsDomainRequest(
            applicationId = applicationId.UUIDString,
            url = url.toUrlString()
        )

    /**
     * @see PlatformClient.addApplicationOwnerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun addApplicationOwner(applicationId: NSUUID, userId: NSUUID) = PlatformClient
        .addApplicationOwnerRequest(
            applicationId = applicationId.UUIDString,
            userId = userId.UUIDString
        )

    /**
     * @see PlatformClient.removeApplicationOwnerRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun removeApplicationOwner(applicationId: NSUUID, userId: NSUUID) = PlatformClient
        .removeApplicationOwnerRequest(
            applicationId = applicationId.UUIDString,
            userId = userId.UUIDString
        )

    /**
     * @see PlatformClient.getApplicationOwnersDetailsRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getApplicationOwnersDetails(
        applicationId: NSUUID
    ): List<ApplicationOwnerDetailsResponse> = PlatformClient
        .getApplicationOwnersDetailsRequest(applicationId.UUIDString)
        .toApplicationOwnerDetailsResponse()
}

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
actual fun platform(): PlatformApi = PlatformApi