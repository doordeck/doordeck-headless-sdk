package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.ResultCallback
import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.AddAuthKeyData
import com.doordeck.multiplatform.sdk.model.data.ApplicationIdData
import com.doordeck.multiplatform.sdk.model.data.ApplicationOwnerData
import com.doordeck.multiplatform.sdk.model.data.ApplicationUserData
import com.doordeck.multiplatform.sdk.model.data.AuthIssuerData
import com.doordeck.multiplatform.sdk.model.data.CorsDomainData
import com.doordeck.multiplatform.sdk.model.data.CreateApplicationData
import com.doordeck.multiplatform.sdk.model.data.GetLogoUploadUrlData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationAppLinkData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationCompanyNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationEmailPreferencesData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationLogoUrlData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationMailingAddressData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationPrivacyPolicyData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationSupportContactData
import com.doordeck.multiplatform.sdk.model.data.toAuthKey
import com.doordeck.multiplatform.sdk.model.data.toCreateApplication
import com.doordeck.multiplatform.sdk.model.data.toEmailPreferences
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.replyAsync

actual object PlatformApi {
    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    fun createApplication(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val createApplicationData = data.fromJson<CreateApplicationData>()
        PlatformClient.createApplicationRequest(createApplicationData.toCreateApplication())
    }

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    fun listApplications(requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        PlatformClient.listApplicationsRequest()
    }

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    fun getApplication(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val applicationIdData = data.fromJson<ApplicationIdData>()
        PlatformClient.getApplicationRequest(applicationIdData.applicationId)
    }

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationName(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val updateApplicationName = data.fromJson<UpdateApplicationNameData>()
        PlatformClient.updateApplicationNameRequest(
            applicationId = updateApplicationName.applicationId,
            name = updateApplicationName.name
        )
    }

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationCompanyName(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val updateApplicationCompanyName = data.fromJson<UpdateApplicationCompanyNameData>()
        PlatformClient.updateApplicationCompanyNameRequest(
            applicationId = updateApplicationCompanyName.applicationId,
            companyName = updateApplicationCompanyName.companyName
        )
    }

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationMailingAddress(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val updateApplicationMailingAddressData = data.fromJson<UpdateApplicationMailingAddressData>()
        PlatformClient.updateApplicationMailingAddressRequest(
            applicationId = updateApplicationMailingAddressData.applicationId,
            mailingAddress = updateApplicationMailingAddressData.mailingAddress
        )
    }

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationPrivacyPolicy(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val updateApplicationPrivacyPolicyData = data.fromJson<UpdateApplicationPrivacyPolicyData>()
        PlatformClient.updateApplicationPrivacyPolicyRequest(
            applicationId = updateApplicationPrivacyPolicyData.applicationId,
            privacyPolicy = updateApplicationPrivacyPolicyData.privacyPolicy
        )
    }

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationSupportContact(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val updateApplicationSupportContactData = data.fromJson<UpdateApplicationSupportContactData>()
        PlatformClient.updateApplicationSupportContactRequest(
            applicationId = updateApplicationSupportContactData.applicationId,
            supportContact = updateApplicationSupportContactData.supportContact
        )
    }

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationAppLink(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val updateApplicationAppLinkData = data.fromJson<UpdateApplicationAppLinkData>()
        PlatformClient.updateApplicationAppLinkRequest(
            applicationId = updateApplicationAppLinkData.applicationId,
            appLink = updateApplicationAppLinkData.appLink
        )
    }

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationEmailPreferences(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val updateApplicationEmailPreferencesData = data.fromJson<UpdateApplicationEmailPreferencesData>()
        PlatformClient.updateApplicationEmailPreferencesRequest(
            applicationId = updateApplicationEmailPreferencesData.applicationId,
            emailPreferences = updateApplicationEmailPreferencesData.emailPreferences.toEmailPreferences()
        )
    }

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationLogoUrl(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val updateApplicationLogoUrlData = data.fromJson<UpdateApplicationLogoUrlData>()
        PlatformClient.updateApplicationLogoUrlRequest(
            applicationId = updateApplicationLogoUrlData.applicationId,
            logoUrl = updateApplicationLogoUrlData.logoUrl
        )
    }

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    fun deleteApplication(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val applicationIdData = data.fromJson<ApplicationIdData>()
        PlatformClient.deleteApplicationRequest(applicationIdData.applicationId)
    }

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    fun getLogoUploadUrl(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val getLogoUploadUrlData = data.fromJson<GetLogoUploadUrlData>()
        PlatformClient.getLogoUploadUrlRequest(
            applicationId = getLogoUploadUrlData.applicationId,
            contentType = getLogoUploadUrlData.contentType
        )
    }

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    fun addAuthKey(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val addAuthKeyData = data.fromJson<AddAuthKeyData>()
        PlatformClient.addAuthKeyRequest(
            applicationId = addAuthKeyData.applicationId,
            key = addAuthKeyData.key.toAuthKey()
        )
    }

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    fun addAuthIssuer(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val authIssuerData = data.fromJson<AuthIssuerData>()
        PlatformClient.addAuthIssuerRequest(
            applicationId = authIssuerData.applicationId,
            url = authIssuerData.url
        )
    }

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    fun deleteAuthIssuer(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val authIssuerData = data.fromJson<AuthIssuerData>()
        PlatformClient.deleteAuthIssuerRequest(
            applicationId = authIssuerData.applicationId,
            url = authIssuerData.url
        )
    }

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    fun addCorsDomain(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val corsDomainData = data.fromJson<CorsDomainData>()
        PlatformClient.addCorsDomainRequest(
            applicationId = corsDomainData.applicationId,
            url = corsDomainData.url
        )
    }

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    fun removeCorsDomain(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val corsDomainData = data.fromJson<CorsDomainData>()
        PlatformClient.removeCorsDomainRequest(
            applicationId = corsDomainData.applicationId,
            url = corsDomainData.url
        )
    }

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    fun addApplicationOwner(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val applicationOwnerData = data.fromJson<ApplicationOwnerData>()
        PlatformClient.addApplicationOwnerRequest(
            applicationId = applicationOwnerData.applicationId,
            userId = applicationOwnerData.userId
        )
    }

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    fun removeApplicationOwner(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val applicationOwnerData = data.fromJson<ApplicationOwnerData>()
        PlatformClient.removeApplicationOwnerRequest(
            applicationId = applicationOwnerData.applicationId,
            userId = applicationOwnerData.userId
        )
    }

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    fun getApplicationOwnersDetails(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val applicationIdData = data.fromJson<ApplicationIdData>()
        PlatformClient.getApplicationOwnersDetailsRequest(applicationIdData.applicationId)
    }

    /**
     * @see PlatformClient.getApplicationUsersRequest
     */
    @DoordeckOnly
    fun getApplicationUsers(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val applicationUserData = data.fromJson<ApplicationUserData>()
        PlatformClient.getApplicationUsersRequest(
            applicationId = applicationUserData.applicationId,
            pageSize = applicationUserData.pageSize,
            lastUserRetrieved = applicationUserData.lastUserRetrieved
        )
    }
}

actual fun platform(): PlatformApi = PlatformApi