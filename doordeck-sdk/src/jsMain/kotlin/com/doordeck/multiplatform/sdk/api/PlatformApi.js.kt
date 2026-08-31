package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.model.data.toBasicAuthKey
import com.doordeck.multiplatform.sdk.model.data.toBasicCreateApplication
import com.doordeck.multiplatform.sdk.model.data.toBasicEmailPreferences
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationUserResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationUserResponse
import com.doordeck.multiplatform.sdk.model.responses.toGetLogoUploadUrlResponse
import kotlin.js.collections.JsArray

/**
 * Platform-specific implementations of platform-related API calls.
 */
@JsExport
actual object PlatformApi {
    /**
     * @see PlatformClient.createApplicationRequest
     */
    @DoordeckOnly
    suspend fun createApplication(application: PlatformOperations.CreateApplication): String = PlatformClient
        .createApplicationRequest(application.toBasicCreateApplication())

    /**
     * @see PlatformClient.listApplicationsRequest
     */
    @DoordeckOnly
    suspend fun listApplications(): JsArray<ApplicationResponse> = PlatformClient
        .listApplicationsRequest()
        .toApplicationResponse()

    /**
     * @see PlatformClient.getApplicationRequest
     */
    @DoordeckOnly
    suspend fun getApplication(applicationId: String): ApplicationResponse = PlatformClient
        .getApplicationRequest(applicationId)
        .toApplicationResponse()

    /**
     * @see PlatformClient.updateApplicationNameRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationName(applicationId: String, name: String): Unit = PlatformClient
        .updateApplicationNameRequest(
            applicationId = applicationId,
            name = name
        )

    /**
     * @see PlatformClient.updateApplicationCompanyNameRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationCompanyName(applicationId: String, companyName: String): Unit = PlatformClient
        .updateApplicationCompanyNameRequest(
            applicationId = applicationId,
            companyName = companyName
        )

    /**
     * @see PlatformClient.updateApplicationMailingAddressRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String): Unit = PlatformClient
        .updateApplicationMailingAddressRequest(
            applicationId = applicationId,
            mailingAddress = mailingAddress
        )

    /**
     * @see PlatformClient.updateApplicationPrivacyPolicyRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String): Unit = PlatformClient
        .updateApplicationPrivacyPolicyRequest(
            applicationId = applicationId,
            privacyPolicy = privacyPolicy
        )

    /**
     * @see PlatformClient.updateApplicationSupportContactRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String): Unit = PlatformClient
        .updateApplicationSupportContactRequest(
            applicationId = applicationId,
            supportContact = supportContact
        )

    /**
     * @see PlatformClient.updateApplicationAppLinkRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationAppLink(applicationId: String, appLink: String): Unit = PlatformClient
        .updateApplicationAppLinkRequest(
            applicationId = applicationId,
            appLink = appLink
        )

    /**
     * @see PlatformClient.updateApplicationEmailPreferencesRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationEmailPreferences(
        applicationId: String,
        emailPreferences: PlatformOperations.EmailPreferences
    ): Unit = PlatformClient
        .updateApplicationEmailPreferencesRequest(
            applicationId = applicationId,
            emailPreferences = emailPreferences.toBasicEmailPreferences()
        )

    /**
     * @see PlatformClient.updateApplicationLogoUrlRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String): Unit = PlatformClient
        .updateApplicationLogoUrlRequest(
            applicationId = applicationId,
            logoUrl = logoUrl
        )

    /**
     * @see PlatformClient.deleteApplicationRequest
     */
    @DoordeckOnly
    suspend fun deleteApplication(applicationId: String): Unit = PlatformClient.deleteApplicationRequest(applicationId)

    /**
     * @see PlatformClient.getLogoUploadUrlRequest
     */
    @DoordeckOnly
    suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse = PlatformClient
        .getLogoUploadUrlRequest(
            applicationId = applicationId,
            contentType = contentType
        )
        .toGetLogoUploadUrlResponse()

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    suspend fun addAuthKey(applicationId: String, key: PlatformOperations.AuthKey): Unit = PlatformClient
        .addAuthKeyRequest(
            applicationId = applicationId,
            key = key.toBasicAuthKey()
        )

    /**
     * @see PlatformClient.addAuthIssuerRequest
     */
    @DoordeckOnly
    suspend fun addAuthIssuer(applicationId: String, url: String): Unit = PlatformClient
        .addAuthIssuerRequest(
            applicationId = applicationId,
            url = url
        )

    /**
     * @see PlatformClient.deleteAuthIssuerRequest
     */
    @DoordeckOnly
    suspend fun deleteAuthIssuer(applicationId: String, url: String): Unit = PlatformClient
        .deleteAuthIssuerRequest(
            applicationId = applicationId,
            url = url
        )

    /**
     * @see PlatformClient.addCorsDomainRequest
     */
    @DoordeckOnly
    suspend fun addCorsDomain(applicationId: String, url: String): Unit = PlatformClient
        .addCorsDomainRequest(
            applicationId = applicationId,
            url = url
        )

    /**
     * @see PlatformClient.removeCorsDomainRequest
     */
    @DoordeckOnly
    suspend fun removeCorsDomain(applicationId: String, url: String): Unit = PlatformClient
        .removeCorsDomainRequest(
            applicationId = applicationId,
            url = url
        )

    /**
     * @see PlatformClient.addApplicationOwnerRequest
     */
    @DoordeckOnly
    suspend fun addApplicationOwner(applicationId: String, userId: String): Unit = PlatformClient
        .addApplicationOwnerRequest(
            applicationId = applicationId,
            userId = userId
        )

    /**
     * @see PlatformClient.removeApplicationOwnerRequest
     */
    @DoordeckOnly
    suspend fun removeApplicationOwner(applicationId: String, userId: String): Unit = PlatformClient
        .removeApplicationOwnerRequest(
            applicationId = applicationId,
            userId = userId
        )

    /**
     * @see PlatformClient.getApplicationOwnersDetailsRequest
     */
    @DoordeckOnly
    suspend fun getApplicationOwnersDetails(
        applicationId: String
    ): JsArray<ApplicationOwnerDetailsResponse> = PlatformClient
        .getApplicationOwnersDetailsRequest(applicationId)
        .toApplicationOwnerDetailsResponse()

    /**
     * @see PlatformClient.getApplicationUsersRequest
     */
    @DoordeckOnly
    suspend fun getApplicationUsers(
        applicationId: String,
        pageSize: Int = 100,
        lastUserRetrieved: String? = null
    ): JsArray<ApplicationUserResponse> = PlatformClient
        .getApplicationUsersRequest(
            applicationId = applicationId,
            pageSize = pageSize,
            lastUserRetrieved = lastUserRetrieved
        ).toApplicationUserResponse()
}

private val platform = PlatformApi

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
actual fun platform(): PlatformApi = platform