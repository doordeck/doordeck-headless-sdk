package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.Application
import com.doordeck.multiplatform.sdk.model.data.ApplicationOwnerDetails
import com.doordeck.multiplatform.sdk.model.data.GetLogoUploadUrl
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.model.data.toApplication
import com.doordeck.multiplatform.sdk.model.data.toApplicationOwnerDetails
import com.doordeck.multiplatform.sdk.model.data.toBasicAuthKey
import com.doordeck.multiplatform.sdk.model.data.toBasicCreateApplication
import com.doordeck.multiplatform.sdk.model.data.toBasicEmailPreferences
import com.doordeck.multiplatform.sdk.model.data.toGetLogoUploadUrl
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
    fun createApplication(application: PlatformOperations.CreateApplication): Promise<dynamic> {
        return promise { PlatformClient.createApplicationRequest(application.toBasicCreateApplication()) }
    }

    /**
     * @see PlatformClient.listApplicationsRequest
     */
    @DoordeckOnly
    fun listApplications(): Promise<List<Application>> {
        return promise { PlatformClient.listApplicationsRequest().toApplication() }
    }

    /**
     * @see PlatformClient.getApplicationRequest
     */
    @DoordeckOnly
    fun getApplication(applicationId: String): Promise<Application> {
        return promise { PlatformClient.getApplicationRequest(applicationId).toApplication() }
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
    fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: PlatformOperations.EmailPreferences): Promise<dynamic> {
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
    fun getLogoUploadUrl(applicationId: String, contentType: String): Promise<GetLogoUploadUrl> {
        return promise { PlatformClient.getLogoUploadUrlRequest(applicationId, contentType).toGetLogoUploadUrl() }
    }

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    fun addAuthKey(applicationId: String, key: PlatformOperations.AuthKey): Promise<dynamic> {
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
    fun getApplicationOwnersDetails(applicationId: String): Promise<List<ApplicationOwnerDetails>> {
        return promise { PlatformClient.getApplicationOwnersDetailsRequest(applicationId).toApplicationOwnerDetails() }
    }
}

private val platform = PlatformApi

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
@JsExport
actual fun platform(): PlatformApi = platform