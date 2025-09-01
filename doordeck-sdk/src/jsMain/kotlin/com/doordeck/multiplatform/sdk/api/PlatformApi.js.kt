package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.model.data.toBasicAuthKey
import com.doordeck.multiplatform.sdk.model.data.toBasicCreateApplication
import com.doordeck.multiplatform.sdk.model.data.toBasicEmailPreferences
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.toApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.toGetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise
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
    fun createApplication(application: PlatformOperations.CreateApplication): Promise<String> = promise {
        PlatformClient.createApplicationRequest(application.toBasicCreateApplication())
    }

    /**
     * @see PlatformClient.listApplicationsRequest
     */
    @DoordeckOnly
    fun listApplications(): Promise<JsArray<ApplicationResponse>> = promise {
        PlatformClient.listApplicationsRequest()
            .toApplicationResponse()
    }

    /**
     * @see PlatformClient.getApplicationRequest
     */
    @DoordeckOnly
    fun getApplication(applicationId: String): Promise<ApplicationResponse> = promise {
        PlatformClient.getApplicationRequest(applicationId)
            .toApplicationResponse()
    }

    /**
     * @see PlatformClient.updateApplicationNameRequest
     */
    @DoordeckOnly
    fun updateApplicationName(applicationId: String, name: String): Promise<dynamic> = promise {
        PlatformClient.updateApplicationNameRequest(
            applicationId = applicationId,
            name = name
        )
    }

    /**
     * @see PlatformClient.updateApplicationCompanyNameRequest
     */
    @DoordeckOnly
    fun updateApplicationCompanyName(applicationId: String, companyName: String): Promise<dynamic> = promise {
        PlatformClient.updateApplicationCompanyNameRequest(
            applicationId = applicationId,
            companyName = companyName
        )
    }

    /**
     * @see PlatformClient.updateApplicationMailingAddressRequest
     */
    @DoordeckOnly
    fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String): Promise<dynamic> = promise {
        PlatformClient.updateApplicationMailingAddressRequest(
            applicationId = applicationId,
            mailingAddress = mailingAddress
        )
    }

    /**
     * @see PlatformClient.updateApplicationPrivacyPolicyRequest
     */
    @DoordeckOnly
    fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String): Promise<dynamic> = promise {
        PlatformClient.updateApplicationPrivacyPolicyRequest(
            applicationId = applicationId,
            privacyPolicy = privacyPolicy
        )
    }

    /**
     * @see PlatformClient.updateApplicationSupportContactRequest
     */
    @DoordeckOnly
    fun updateApplicationSupportContact(applicationId: String, supportContact: String): Promise<dynamic> = promise {
        PlatformClient.updateApplicationSupportContactRequest(
            applicationId = applicationId,
            supportContact = supportContact
        )
    }

    /**
     * @see PlatformClient.updateApplicationAppLinkRequest
     */
    @DoordeckOnly
    fun updateApplicationAppLink(applicationId: String, appLink: String): Promise<dynamic> = promise {
        PlatformClient.updateApplicationAppLinkRequest(
            applicationId = applicationId,
            appLink = appLink
        )
    }

    /**
     * @see PlatformClient.updateApplicationEmailPreferencesRequest
     */
    @DoordeckOnly
    fun updateApplicationEmailPreferences(
        applicationId: String,
        emailPreferences: PlatformOperations.EmailPreferences
    ): Promise<dynamic> = promise {
        PlatformClient.updateApplicationEmailPreferencesRequest(
            applicationId = applicationId,
            emailPreferences = emailPreferences.toBasicEmailPreferences()
        )
    }

    /**
     * @see PlatformClient.updateApplicationLogoUrlRequest
     */
    @DoordeckOnly
    fun updateApplicationLogoUrl(applicationId: String, logoUrl: String): Promise<dynamic> = promise {
        PlatformClient.updateApplicationLogoUrlRequest(
            applicationId = applicationId,
            logoUrl = logoUrl
        )
    }

    /**
     * @see PlatformClient.deleteApplicationRequest
     */
    @DoordeckOnly
    fun deleteApplication(applicationId: String): Promise<dynamic> = promise {
        PlatformClient.deleteApplicationRequest(applicationId)
    }

    /**
     * @see PlatformClient.getLogoUploadUrlRequest
     */
    @DoordeckOnly
    fun getLogoUploadUrl(applicationId: String, contentType: String): Promise<GetLogoUploadUrlResponse> = promise {
        PlatformClient
            .getLogoUploadUrlRequest(
                applicationId = applicationId,
                contentType = contentType
            )
            .toGetLogoUploadUrlResponse()
    }

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    fun addAuthKey(applicationId: String, key: PlatformOperations.AuthKey): Promise<dynamic> = promise {
        PlatformClient.addAuthKeyRequest(
            applicationId = applicationId,
            key = key.toBasicAuthKey()
        )
    }

    /**
     * @see PlatformClient.addAuthIssuerRequest
     */
    @DoordeckOnly
    fun addAuthIssuer(applicationId: String, url: String): Promise<dynamic> = promise {
        PlatformClient.addAuthIssuerRequest(
            applicationId = applicationId,
            url = url
        )
    }

    /**
     * @see PlatformClient.deleteAuthIssuerRequest
     */
    @DoordeckOnly
    fun deleteAuthIssuer(applicationId: String, url: String): Promise<dynamic> = promise {
        PlatformClient.deleteAuthIssuerRequest(
            applicationId = applicationId,
            url = url
        )
    }

    /**
     * @see PlatformClient.addCorsDomainRequest
     */
    @DoordeckOnly
    fun addCorsDomain(applicationId: String, url: String): Promise<dynamic> = promise {
        PlatformClient.addCorsDomainRequest(
            applicationId = applicationId,
            url = url
        )
    }

    /**
     * @see PlatformClient.removeCorsDomainRequest
     */
    @DoordeckOnly
    fun removeCorsDomain(applicationId: String, url: String): Promise<dynamic> = promise {
        PlatformClient.removeCorsDomainRequest(
            applicationId = applicationId,
            url = url
        )
    }

    /**
     * @see PlatformClient.addApplicationOwnerRequest
     */
    @DoordeckOnly
    fun addApplicationOwner(applicationId: String, userId: String): Promise<dynamic> = promise {
        PlatformClient.addApplicationOwnerRequest(
            applicationId = applicationId,
            userId = userId
        )
    }

    /**
     * @see PlatformClient.removeApplicationOwnerRequest
     */
    @DoordeckOnly
    fun removeApplicationOwner(applicationId: String, userId: String): Promise<dynamic> = promise {
        PlatformClient.removeApplicationOwnerRequest(
            applicationId = applicationId,
            userId = userId
        )
    }

    /**
     * @see PlatformClient.getApplicationOwnersDetailsRequest
     */
    @DoordeckOnly
    fun getApplicationOwnersDetails(
        applicationId: String
    ): Promise<JsArray<ApplicationOwnerDetailsResponse>> = promise {
        PlatformClient.getApplicationOwnersDetailsRequest(applicationId)
            .toApplicationOwnerDetailsResponse()
    }
}

private val platform = PlatformApi

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
@JsExport
actual fun platform(): PlatformApi = platform