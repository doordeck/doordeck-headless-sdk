package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import com.doordeck.multiplatform.sdk.internal.api.PlatformClient
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import org.koin.mp.KoinPlatform.getKoin

actual interface PlatformResource {
    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    fun createApplication(application: Platform.CreateApplication)
    @DoordeckOnly
    fun createApplicationJson(data: String)

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    fun listApplications(): List<ApplicationResponse>
    @DoordeckOnly
    fun listApplicationsJson(): String

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    fun getApplication(applicationId: String): ApplicationResponse
    @DoordeckOnly
    fun getApplicationJson(data: String): String

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationName(applicationId: String, name: String)
    @DoordeckOnly
    fun updateApplicationNameJson(data: String)

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationCompanyName(applicationId: String, companyName: String)
    @DoordeckOnly
    fun updateApplicationCompanyNameJson(data: String)

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String)
    @DoordeckOnly
    fun updateApplicationMailingAddressJson(data: String)

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String)
    @DoordeckOnly
    fun updateApplicationPrivacyPolicyJson(data: String)

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationSupportContact(applicationId: String, supportContact: String)
    @DoordeckOnly
    fun updateApplicationSupportContactJson(data: String)

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationAppLink(applicationId: String, appLink: String)
    @DoordeckOnly
    fun updateApplicationAppLinkJson(data: String)

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences)
    @DoordeckOnly
    fun updateApplicationEmailPreferencesJson(data: String)

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationLogoUrl(applicationId: String, logoUrl: String)
    @DoordeckOnly
    fun updateApplicationLogoUrlJson(data: String)

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    fun deleteApplication(applicationId: String)
    @DoordeckOnly
    fun deleteApplicationJson(data: String)

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse
    @DoordeckOnly
    fun getLogoUploadUrlJson(data: String): String

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    fun addAuthKey(applicationId: String, key: Platform.AuthKey)
    @DoordeckOnly
    fun addAuthKeyJson(data: String)

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    fun addAuthIssuer(applicationId: String, url: String)
    @DoordeckOnly
    fun addAuthIssuerJson(data: String)

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    fun deleteAuthIssuer(applicationId: String, url: String)
    @DoordeckOnly
    fun deleteAuthIssuerJson(data: String)

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    fun addCorsDomain(applicationId: String, url: String)
    @DoordeckOnly
    fun addCorsDomainJson(data: String)

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    fun removeCorsDomain(applicationId: String, url: String)
    @DoordeckOnly
    fun removeCorsDomainJson(data: String)

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    fun addApplicationOwner(applicationId: String, userId: String)
    @DoordeckOnly
    fun addApplicationOwnerJson(data: String)

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    fun removeApplicationOwner(applicationId: String, userId: String)
    @DoordeckOnly
    fun removeApplicationOwnerJson(data: String)

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse>
    @DoordeckOnly
    fun getApplicationOwnersDetailsJson(data: String): String
}

actual fun platform(): PlatformResource = PlatformResourceImpl(getKoin().get<PlatformClient>())