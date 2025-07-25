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
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

/**
 * Platform-specific implementations of platform-related API calls.
 */
@JsExport
actual object PlatformApi {
    /**
     * @see PlatformClient.createApplicationRequest
     */
    @DoordeckOnly
    fun createApplication(application: Platform.CreateApplication): Promise<dynamic> {
        return promise { PlatformClient.createApplicationRequest(application.toBasicCreateApplication()) }
    }

    /**
     * @see PlatformClient.listApplicationsRequest
     */
    @DoordeckOnly
    fun listApplications(): Promise<List<ApplicationResponse>> {
        return promise { PlatformClient.listApplicationsRequest().toApplicationResponse() }
    }

    /**
     * @see PlatformClient.getApplicationRequest
     */
    @DoordeckOnly
    fun getApplication(applicationId: String): Promise<ApplicationResponse> {
        return promise { PlatformClient.getApplicationRequest(applicationId).toApplicationResponse() }
    }

    /**
     * @see PlatformClient.updateApplicationNameRequest
     */
    @DoordeckOnly
    fun updateApplicationName(applicationId: String, name: String): Promise<dynamic> {
        return promise { PlatformClient.updateApplicationNameRequest(applicationId, name) }
    }

    /**
     * @see PlatformClient.updateApplicationCompanyNameRequest
     */
    @DoordeckOnly
    fun updateApplicationCompanyName(applicationId: String, companyName: String): Promise<dynamic> {
        return promise { PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    /**
     * @see PlatformClient.updateApplicationMailingAddressRequest
     */
    @DoordeckOnly
    fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String): Promise<dynamic> {
        return promise { PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    /**
     * @see PlatformClient.updateApplicationPrivacyPolicyRequest
     */
    @DoordeckOnly
    fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String): Promise<dynamic> {
        return promise { PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    /**
     * @see PlatformClient.updateApplicationSupportContactRequest
     */
    @DoordeckOnly
    fun updateApplicationSupportContact(applicationId: String, supportContact: String): Promise<dynamic> {
        return promise { PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact)}
    }

    /**
     * @see PlatformClient.updateApplicationAppLinkRequest
     */
    @DoordeckOnly
    fun updateApplicationAppLink(applicationId: String, appLink: String): Promise<dynamic> {
        return promise { PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    /**
     * @see PlatformClient.updateApplicationEmailPreferencesRequest
     */
    @DoordeckOnly
    fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences): Promise<dynamic> {
        return promise { PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences.toBasicEmailPreferences()) }
    }

    /**
     * @see PlatformClient.updateApplicationLogoUrlRequest
     */
    @DoordeckOnly
    fun updateApplicationLogoUrl(applicationId: String, logoUrl: String): Promise<dynamic> {
        return promise { PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    /**
     * @see PlatformClient.deleteApplicationRequest
     */
    @DoordeckOnly
    fun deleteApplication(applicationId: String): Promise<dynamic> {
        return promise { PlatformClient.deleteApplicationRequest(applicationId) }
    }

    /**
     * @see PlatformClient.getLogoUploadUrlRequest
     */
    @DoordeckOnly
    fun getLogoUploadUrl(applicationId: String, contentType: String): Promise<GetLogoUploadUrlResponse> {
        return promise { PlatformClient.getLogoUploadUrlRequest(applicationId, contentType).toGetLogoUploadUrlResponse() }
    }

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    fun addAuthKey(applicationId: String, key: Platform.AuthKey): Promise<dynamic> {
        return promise { PlatformClient.addAuthKeyRequest(applicationId, key.toBasicAuthKey()) }
    }

    /**
     * @see PlatformClient.addAuthIssuerRequest
     */
    @DoordeckOnly
    fun addAuthIssuer(applicationId: String, url: String): Promise<dynamic> {
        return promise { PlatformClient.addAuthIssuerRequest(applicationId, url) }
    }

    /**
     * @see PlatformClient.deleteAuthIssuerRequest
     */
    @DoordeckOnly
    fun deleteAuthIssuer(applicationId: String, url: String): Promise<dynamic> {
        return promise { PlatformClient.deleteAuthIssuerRequest(applicationId, url) }
    }

    /**
     * @see PlatformClient.addCorsDomainRequest
     */
    @DoordeckOnly
    fun addCorsDomain(applicationId: String, url: String): Promise<dynamic> {
        return promise { PlatformClient.addCorsDomainRequest(applicationId, url) }
    }

    /**
     * @see PlatformClient.removeCorsDomainRequest
     */
    @DoordeckOnly
    fun removeCorsDomain(applicationId: String, url: String): Promise<dynamic> {
        return promise { PlatformClient.removeCorsDomainRequest(applicationId, url) }
    }

    /**
     * @see PlatformClient.addApplicationOwnerRequest
     */
    @DoordeckOnly
    fun addApplicationOwner(applicationId: String, userId: String): Promise<dynamic> {
        return promise { PlatformClient.addApplicationOwnerRequest(applicationId, userId) }
    }

    /**
     * @see PlatformClient.removeApplicationOwnerRequest
     */
    @DoordeckOnly
    fun removeApplicationOwner(applicationId: String, userId: String): Promise<dynamic> {
        return promise { PlatformClient.removeApplicationOwnerRequest(applicationId, userId) }
    }

    /**
     * @see PlatformClient.getApplicationOwnersDetailsRequest
     */
    @DoordeckOnly
    fun getApplicationOwnersDetails(applicationId: String): Promise<List<ApplicationOwnerDetailsResponse>> {
        return promise { PlatformClient.getApplicationOwnersDetailsRequest(applicationId).toApplicationOwnerDetailsResponse() }
    }
}

private val platform = PlatformApi

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
@JsExport
actual fun platform(): PlatformApi = platform