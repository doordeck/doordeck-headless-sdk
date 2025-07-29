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
import com.doordeck.multiplatform.sdk.util.completableFuture
import com.nimbusds.jose.jwk.JWK
import java.net.URI
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of platform-related API calls.
 */
actual object PlatformApi {
    /**
     * @see PlatformClient.createApplicationRequest
     */
    @DoordeckOnly
    suspend fun createApplication(application: PlatformOperations.CreateApplication) {
        return PlatformClient.createApplicationRequest(application.toBasicCreateApplication())
    }

    /**
     * Async variant of [PlatformApi.createApplication] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun createApplicationAsync(application: PlatformOperations.CreateApplication): CompletableFuture<Unit> {
        return completableFuture { createApplication(application) }
    }

    /**
     * @see PlatformClient.listApplicationsRequest
     */
    @DoordeckOnly
    suspend fun listApplications(): List<ApplicationResponse> {
        return PlatformClient.listApplicationsRequest()
            .toApplicationResponse()
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
    suspend fun getApplication(applicationId: UUID): ApplicationResponse {
        return PlatformClient.getApplicationRequest(applicationId.toString())
            .toApplicationResponse()
    }

    /**
     * Async variant of [PlatformApi.getApplication] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getApplicationAsync(applicationId: UUID): CompletableFuture<ApplicationResponse> {
        return completableFuture { getApplication(applicationId) }
    }

    /**
     * @see PlatformClient.updateApplicationNameRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationName(applicationId: UUID, name: String) {
        return PlatformClient.updateApplicationNameRequest(
            applicationId = applicationId.toString(),
            name = name
        )
    }

    /**
     * Async variant of [PlatformApi.updateApplicationName] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationNameAsync(applicationId: UUID, name: String): CompletableFuture<Unit> {
        return completableFuture {
            updateApplicationName(
                applicationId = applicationId,
                name = name
            )
        }
    }

    /**
     * @see PlatformClient.updateApplicationCompanyNameRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationCompanyName(applicationId: UUID, companyName: String) {
        return PlatformClient.updateApplicationCompanyNameRequest(
            applicationId = applicationId.toString(),
            companyName = companyName
        )
    }

    /**
     * Async variant of [PlatformApi.updateApplicationCompanyName] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationCompanyNameAsync(applicationId: UUID, companyName: String): CompletableFuture<Unit> {
        return completableFuture {
            updateApplicationCompanyName(
                applicationId = applicationId,
                companyName = companyName
            )
        }
    }

    /**
     * @see PlatformClient.updateApplicationMailingAddressRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationMailingAddress(applicationId: UUID, mailingAddress: String) {
        return PlatformClient.updateApplicationMailingAddressRequest(
            applicationId = applicationId.toString(),
            mailingAddress = mailingAddress
        )
    }

    /**
     * Async variant of [PlatformApi.updateApplicationMailingAddress] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationMailingAddressAsync(applicationId: UUID, mailingAddress: String): CompletableFuture<Unit> {
        return completableFuture {
            updateApplicationMailingAddress(
                applicationId = applicationId,
                mailingAddress = mailingAddress
            )
        }
    }

    /**
     * @see PlatformClient.updateApplicationPrivacyPolicyRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationPrivacyPolicy(applicationId: UUID, privacyPolicy: URI) {
        return PlatformClient.updateApplicationPrivacyPolicyRequest(
            applicationId = applicationId.toString(),
            privacyPolicy = privacyPolicy.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.updateApplicationPrivacyPolicy] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationPrivacyPolicyAsync(applicationId: UUID, privacyPolicy: URI): CompletableFuture<Unit> {
        return completableFuture {
            updateApplicationPrivacyPolicy(
                applicationId = applicationId,
                privacyPolicy = privacyPolicy
            )
        }
    }

    /**
     * @see PlatformClient.updateApplicationSupportContactRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationSupportContact(applicationId: UUID, supportContact: URI) {
        return PlatformClient.updateApplicationSupportContactRequest(
            applicationId = applicationId.toString(),
            supportContact = supportContact.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.updateApplicationSupportContact] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationSupportContactAsync(applicationId: UUID, supportContact: URI): CompletableFuture<Unit> {
        return completableFuture {
            updateApplicationSupportContact(
                applicationId = applicationId,
                supportContact = supportContact
            )
        }
    }

    /**
     * @see PlatformClient.updateApplicationAppLinkRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationAppLink(applicationId: UUID, appLink: URI) {
        return PlatformClient.updateApplicationAppLinkRequest(
            applicationId = applicationId.toString(),
            appLink = appLink.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.updateApplicationAppLink] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationAppLinkAsync(applicationId: UUID, appLink: URI): CompletableFuture<Unit> {
        return completableFuture {
            updateApplicationAppLink(
                applicationId = applicationId,
                appLink = appLink
            )
        }
    }

    /**
     * @see PlatformClient.updateApplicationEmailPreferencesRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationEmailPreferences(
        applicationId: UUID,
        emailPreferences: PlatformOperations.EmailPreferences
    ) {
        return PlatformClient.updateApplicationEmailPreferencesRequest(
            applicationId = applicationId.toString(),
            emailPreferences = emailPreferences.toBasicEmailPreferences()
        )
    }

    /**
     * Async variant of [PlatformApi.updateApplicationEmailPreferences] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationEmailPreferencesAsync(
        applicationId: UUID,
        emailPreferences: PlatformOperations.EmailPreferences
    ): CompletableFuture<Unit> {
        return completableFuture {
            updateApplicationEmailPreferences(
                applicationId = applicationId,
                emailPreferences = emailPreferences
            )
        }
    }

    /**
     * @see PlatformClient.updateApplicationLogoUrlRequest
     */
    @DoordeckOnly
    suspend fun updateApplicationLogoUrl(applicationId: UUID, logoUrl: URI) {
        return PlatformClient.updateApplicationLogoUrlRequest(
            applicationId = applicationId.toString(),
            logoUrl = logoUrl.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.updateApplicationLogoUrl] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun updateApplicationLogoUrlAsync(applicationId: UUID, logoUrl: URI): CompletableFuture<Unit> {
        return completableFuture {
            updateApplicationLogoUrl(
                applicationId = applicationId,
                logoUrl = logoUrl
            )
        }
    }

    /**
     * @see PlatformClient.deleteApplicationRequest
     */
    @DoordeckOnly
    suspend fun deleteApplication(applicationId: UUID) {
        return PlatformClient.deleteApplicationRequest(applicationId.toString())
    }

    /**
     * Async variant of [PlatformApi.deleteApplication] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun deleteApplicationAsync(applicationId: UUID): CompletableFuture<Unit> {
        return completableFuture { deleteApplication(applicationId) }
    }

    /**
     * @see PlatformClient.getLogoUploadUrlRequest
     */
    @DoordeckOnly
    suspend fun getLogoUploadUrl(applicationId: UUID, contentType: String): GetLogoUploadUrlResponse {
        return PlatformClient.getLogoUploadUrlRequest(
            applicationId = applicationId.toString(),
            contentType = contentType
        ).toGetLogoUploadUrlResponse()
    }

    /**
     * Async variant of [PlatformApi.getLogoUploadUrl] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getLogoUploadUrlAsync(applicationId: UUID, contentType: String): CompletableFuture<GetLogoUploadUrlResponse> {
        return completableFuture {
            getLogoUploadUrl(
                applicationId = applicationId,
                contentType = contentType
            )
        }
    }

    /**
     * @see PlatformClient.addAuthKeyRequest
     */
    @DoordeckOnly
    suspend fun addAuthKey(applicationId: UUID, key: JWK) {
        return PlatformClient.addAuthKeyRequest(
            applicationId = applicationId.toString(),
            key = key.toBasicAuthKey()
        )
    }

    /**
     * Async variant of [PlatformApi.addAuthKey] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun addAuthKeyAsync(applicationId: UUID, key: JWK): CompletableFuture<Unit> {
        return completableFuture {
            addAuthKey(
                applicationId = applicationId,
                key = key
            )
        }
    }

    /**
     * @see PlatformClient.addAuthIssuerRequest
     */
    @DoordeckOnly
    suspend fun addAuthIssuer(applicationId: UUID, url: URI) {
        return PlatformClient.addAuthIssuerRequest(
            applicationId = applicationId.toString(),
            url = url.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.addAuthIssuer] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun addAuthIssuerAsync(applicationId: UUID, url: URI): CompletableFuture<Unit> {
        return completableFuture {
            addAuthIssuer(
                applicationId = applicationId,
                url = url
            )
        }
    }

    /**
     * @see PlatformClient.deleteAuthIssuerRequest
     */
    @DoordeckOnly
    suspend fun deleteAuthIssuer(applicationId: UUID, url: URI) {
        return PlatformClient.deleteAuthIssuerRequest(
            applicationId = applicationId.toString(),
            url = url.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.deleteAuthIssuer] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun deleteAuthIssuerAsync(applicationId: UUID, url: URI): CompletableFuture<Unit> {
        return completableFuture {
            deleteAuthIssuer(
                applicationId = applicationId,
                url = url
            )
        }
    }

    /**
     * @see PlatformClient.addCorsDomainRequest
     */
    @DoordeckOnly
    suspend fun addCorsDomain(applicationId: UUID, url: URI) {
        return PlatformClient.addCorsDomainRequest(
            applicationId = applicationId.toString(),
            url = url.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.addCorsDomain] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun addCorsDomainAsync(applicationId: UUID, url: URI): CompletableFuture<Unit> {
        return completableFuture {
            addCorsDomain(
                applicationId = applicationId,
                url = url
            )
        }
    }

    /**
     * @see PlatformClient.removeCorsDomainRequest
     */
    @DoordeckOnly
    suspend fun removeCorsDomain(applicationId: UUID, url: URI) {
        return PlatformClient.removeCorsDomainRequest(
            applicationId = applicationId.toString(),
            url = url.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.removeCorsDomain] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun removeCorsDomainAsync(applicationId: UUID, url: URI): CompletableFuture<Unit> {
        return completableFuture {
            removeCorsDomain(
                applicationId = applicationId,
                url = url
            )
        }
    }

    /**
     * @see PlatformClient.addApplicationOwnerRequest
     */
    @DoordeckOnly
    suspend fun addApplicationOwner(applicationId: UUID, userId: UUID) {
        return PlatformClient.addApplicationOwnerRequest(
            applicationId = applicationId.toString(),
            userId = userId.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.addApplicationOwner] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun addApplicationOwnerAsync(applicationId: UUID, userId: UUID): CompletableFuture<Unit> {
        return completableFuture {
            addApplicationOwner(
                applicationId = applicationId,
                userId = userId
            )
        }
    }

    /**
     * @see PlatformClient.removeApplicationOwnerRequest
     */
    @DoordeckOnly
    suspend fun removeApplicationOwner(applicationId: UUID, userId: UUID) {
        return PlatformClient.removeApplicationOwnerRequest(
            applicationId = applicationId.toString(),
            userId = userId.toString()
        )
    }

    /**
     * Async variant of [PlatformApi.removeApplicationOwner] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun removeApplicationOwnerAsync(applicationId: UUID, userId: UUID): CompletableFuture<Unit> {
        return completableFuture {
            removeApplicationOwner(
                applicationId = applicationId,
                userId = userId
            )
        }
    }

    /**
     * @see PlatformClient.getApplicationOwnersDetailsRequest
     */
    @DoordeckOnly
    suspend fun getApplicationOwnersDetails(applicationId: UUID): List<ApplicationOwnerDetailsResponse> {
        return PlatformClient.getApplicationOwnersDetailsRequest(applicationId.toString())
            .toApplicationOwnerDetailsResponse()
    }

    /**
     * Async variant of [PlatformApi.getApplicationOwnersDetails] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getApplicationOwnersDetailsAsync(applicationId: UUID): CompletableFuture<List<ApplicationOwnerDetailsResponse>> {
        return completableFuture { getApplicationOwnersDetails(applicationId) }
    }
}

/**
 * Defines the platform-specific implementation of [PlatformApi]
 */
actual fun platform(): PlatformApi = PlatformApi