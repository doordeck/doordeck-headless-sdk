package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl

actual interface PlatformResource {
    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    fun createApplication(application: Platform.CreateApplication)

    @DoordeckOnly
    @CName("createApplicationJson")
    fun createApplicationJson(data: String): String

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    fun listApplications(): List<ApplicationResponse>

    @DoordeckOnly
    @CName("listApplicationsJson")
    fun listApplicationsJson(): String

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    fun getApplication(applicationId: String): ApplicationResponse

    @DoordeckOnly
    @CName("getApplicationJson")
    fun getApplicationJson(data: String): String

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationName(applicationId: String, name: String)

    @DoordeckOnly
    @CName("updateApplicationNameJson")
    fun updateApplicationNameJson(data: String): String

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationCompanyName(applicationId: String, companyName: String)

    @DoordeckOnly
    @CName("updateApplicationCompanyNameJson")
    fun updateApplicationCompanyNameJson(data: String): String

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String)

    @DoordeckOnly
    @CName("updateApplicationMailingAddressJson")
    fun updateApplicationMailingAddressJson(data: String): String

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String)

    @DoordeckOnly
    @CName("updateApplicationPrivacyPolicyJson")
    fun updateApplicationPrivacyPolicyJson(data: String): String

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationSupportContact(applicationId: String, supportContact: String)

    @DoordeckOnly
    @CName("updateApplicationSupportContactJson")
    fun updateApplicationSupportContactJson(data: String): String

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationAppLink(applicationId: String, appLink: String)

    @DoordeckOnly
    @CName("updateApplicationAppLinkJson")
    fun updateApplicationAppLinkJson(data: String): String

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences)

    @DoordeckOnly
    @CName("updateApplicationEmailPreferencesJson")
    fun updateApplicationEmailPreferencesJson(data: String): String

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationLogoUrl(applicationId: String, logoUrl: String)

    @DoordeckOnly
    @CName("updateApplicationLogoUrlJson")
    fun updateApplicationLogoUrlJson(data: String): String

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    fun deleteApplication(applicationId: String)

    @DoordeckOnly
    @CName("deleteApplicationJson")
    fun deleteApplicationJson(data: String): String

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse

    @DoordeckOnly
    @CName("getLogoUploadUrlJson")
    fun getLogoUploadUrlJson(data: String): String

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    fun addAuthKey(applicationId: String, key: Platform.AuthKey)

    @DoordeckOnly
    @CName("addAuthKeyJson")
    fun addAuthKeyJson(data: String): String

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    fun addAuthIssuer(applicationId: String, url: String)

    @DoordeckOnly
    @CName("addAuthIssuerJson")
    fun addAuthIssuerJson(data: String): String

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    fun deleteAuthIssuer(applicationId: String, url: String)

    @DoordeckOnly
    @CName("deleteAuthIssuerJson")
    fun deleteAuthIssuerJson(data: String): String

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    fun addCorsDomain(applicationId: String, url: String)

    @DoordeckOnly
    @CName("addCorsDomainJson")
    fun addCorsDomainJson(data: String): String

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    fun removeCorsDomain(applicationId: String, url: String)

    @DoordeckOnly
    @CName("removeCorsDomainJson")
    fun removeCorsDomainJson(data: String): String

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    fun addApplicationOwner(applicationId: String, userId: String)

    @DoordeckOnly
    @CName("addApplicationOwnerJson")
    fun addApplicationOwnerJson(data: String): String

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    fun removeApplicationOwner(applicationId: String, userId: String)

    @DoordeckOnly
    @CName("removeApplicationOwnerJson")
    fun removeApplicationOwnerJson(data: String): String

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse>

    @DoordeckOnly
    @CName("getApplicationOwnersDetailsJson")
    fun getApplicationOwnersDetailsJson(data: String): String
}

actual fun platform(): PlatformResource = PlatformResourceImpl