package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface PlatformResource {
    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun createApplication(application: Platform.CreateApplication)

    @DoordeckOnly
    fun createApplicationAsync(application: Platform.CreateApplication): CompletableFuture<Unit>

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    suspend fun listApplications(): List<ApplicationResponse>

    @DoordeckOnly
    fun listApplicationsAsync(): CompletableFuture<List<ApplicationResponse>>

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getApplication(applicationId: String): ApplicationResponse

    @DoordeckOnly
    fun getApplicationAsync(applicationId: String): CompletableFuture<ApplicationResponse>

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationName(applicationId: String, name: String)

    @DoordeckOnly
    fun updateApplicationNameAsync(applicationId: String, name: String): CompletableFuture<Unit>

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationCompanyName(applicationId: String, companyName: String)

    @DoordeckOnly
    fun updateApplicationCompanyNameAsync(applicationId: String, companyName: String): CompletableFuture<Unit>

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String)

    @DoordeckOnly
    fun updateApplicationMailingAddressAsync(applicationId: String, mailingAddress: String): CompletableFuture<Unit>

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String)

    @DoordeckOnly
    fun updateApplicationPrivacyPolicyAsync(applicationId: String, privacyPolicy: String): CompletableFuture<Unit>

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String)

    @DoordeckOnly
    fun updateApplicationSupportContactAsync(applicationId: String, supportContact: String): CompletableFuture<Unit>

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationAppLink(applicationId: String, appLink: String)

    @DoordeckOnly
    fun updateApplicationAppLinkAsync(applicationId: String, appLink: String): CompletableFuture<Unit>

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences)

    @DoordeckOnly
    fun updateApplicationEmailPreferencesAsync(applicationId: String, emailPreferences: Platform.EmailPreferences): CompletableFuture<Unit>

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String)

    @DoordeckOnly
    fun updateApplicationLogoUrlAsync(applicationId: String, logoUrl: String): CompletableFuture<Unit>

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    suspend fun deleteApplication(applicationId: String)

    @DoordeckOnly
    fun deleteApplicationAsync(applicationId: String): CompletableFuture<Unit>

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse

    @DoordeckOnly
    fun getLogoUploadUrlAsync(applicationId: String, contentType: String): CompletableFuture<GetLogoUploadUrlResponse>

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addAuthKey(applicationId: String, key: Platform.AuthKey)

    @DoordeckOnly
    fun addAuthKeyAsync(applicationId: String, key: Platform.AuthKey): CompletableFuture<Unit>

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addAuthIssuer(applicationId: String, url: String)

    @DoordeckOnly
    fun addAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit>

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    suspend fun deleteAuthIssuer(applicationId: String, url: String)

    @DoordeckOnly
    fun deleteAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit>

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addCorsDomain(applicationId: String, url: String)

    @DoordeckOnly
    fun addCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit>

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    suspend fun removeCorsDomain(applicationId: String, url: String)

    @DoordeckOnly
    fun removeCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit>

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    suspend fun addApplicationOwner(applicationId: String, userId: String)

    @DoordeckOnly
    fun addApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit>

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    suspend fun removeApplicationOwner(applicationId: String, userId: String)

    @DoordeckOnly
    fun removeApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit>

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse>

    @DoordeckOnly
    fun getApplicationOwnersDetailsAsync(applicationId: String): CompletableFuture<List<ApplicationOwnerDetailsResponse>>
}

actual fun platform(): PlatformResource = PlatformResourceImpl(getKoin().get<HttpClient>(named("cloudHttpClient")))