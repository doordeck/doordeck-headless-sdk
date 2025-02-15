package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.AddApplicationOwnerData
import com.doordeck.multiplatform.sdk.model.AddAuthIssuerData
import com.doordeck.multiplatform.sdk.model.AddAuthKeyData
import com.doordeck.multiplatform.sdk.model.AddCorsDomainData
import com.doordeck.multiplatform.sdk.model.CreateApplicationData
import com.doordeck.multiplatform.sdk.model.DeleteApplicationData
import com.doordeck.multiplatform.sdk.model.DeleteAuthIssuerData
import com.doordeck.multiplatform.sdk.model.GetApplicationData
import com.doordeck.multiplatform.sdk.model.GetApplicationOwnersDetailsData
import com.doordeck.multiplatform.sdk.model.GetLogoUploadUrlData
import com.doordeck.multiplatform.sdk.model.Platform
import com.doordeck.multiplatform.sdk.model.RemoveApplicationOwnerData
import com.doordeck.multiplatform.sdk.model.RemoveCorsDomainData
import com.doordeck.multiplatform.sdk.model.UpdateApplicationAppLinkData
import com.doordeck.multiplatform.sdk.model.UpdateApplicationCompanyNameData
import com.doordeck.multiplatform.sdk.model.UpdateApplicationEmailPreferencesData
import com.doordeck.multiplatform.sdk.model.UpdateApplicationLogoUrlData
import com.doordeck.multiplatform.sdk.model.UpdateApplicationMailingAddressData
import com.doordeck.multiplatform.sdk.model.UpdateApplicationNameData
import com.doordeck.multiplatform.sdk.model.UpdateApplicationPrivacyPolicyData
import com.doordeck.multiplatform.sdk.model.UpdateApplicationSupportContactData
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.toAuthKey
import com.doordeck.multiplatform.sdk.model.toCreateApplication
import com.doordeck.multiplatform.sdk.model.toEmailPreferences
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

actual object PlatformApi {
    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    fun createApplication(application: Platform.CreateApplication) {
        return runBlocking { PlatformClient.createApplicationRequest(application) }
    }

    @DoordeckOnly
    @CName("createApplicationJson")
    fun createApplicationJson(data: String): String {
        return resultData {
            val createApplicationData = data.fromJson<CreateApplicationData>()
            createApplication(createApplicationData.toCreateApplication())
        }
    }

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    fun listApplications(): List<ApplicationResponse> {
        return runBlocking { PlatformClient.listApplicationsRequest() }
    }

    @DoordeckOnly
    @CName("listApplicationsJson")
    fun listApplicationsJson(): String {
        return resultData {
            listApplications()
        }
    }

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    fun getApplication(applicationId: String): ApplicationResponse {
        return runBlocking { PlatformClient.getApplicationRequest(applicationId) }
    }

    @DoordeckOnly
    @CName("getApplicationJson")
    fun getApplicationJson(data: String): String {
        return resultData {
            val getApplicationData = data.fromJson<GetApplicationData>()
            getApplication(getApplicationData.applicationId)
        }
    }

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationName(applicationId: String, name: String) {
        return runBlocking { PlatformClient.updateApplicationNameRequest(applicationId, name) }
    }

    @DoordeckOnly
    @CName("updateApplicationNameJson")
    fun updateApplicationNameJson(data: String): String {
        return resultData {
            val updateApplicationName = data.fromJson<UpdateApplicationNameData>()
            updateApplicationName(updateApplicationName.applicationId, updateApplicationName.name)
        }
    }

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return runBlocking { PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    @DoordeckOnly
    @CName("updateApplicationCompanyNameJson")
    fun updateApplicationCompanyNameJson(data: String): String {
        return resultData {
            val updateApplicationCompanyName = data.fromJson<UpdateApplicationCompanyNameData>()
            updateApplicationCompanyName(
                applicationId = updateApplicationCompanyName.applicationId,
                companyName = updateApplicationCompanyName.companyName
            )
        }
    }

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return runBlocking { PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    @DoordeckOnly
    @CName("updateApplicationMailingAddressJson")
    fun updateApplicationMailingAddressJson(data: String): String {
        return resultData {
            val updateApplicationMailingAddressData = data.fromJson<UpdateApplicationMailingAddressData>()
            updateApplicationMailingAddress(
                applicationId = updateApplicationMailingAddressData.applicationId,
                mailingAddress = updateApplicationMailingAddressData.mailingAddress
            )
        }
    }

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return runBlocking { PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    @DoordeckOnly
    @CName("updateApplicationPrivacyPolicyJson")
    fun updateApplicationPrivacyPolicyJson(data: String): String {
        return resultData {
            val updateApplicationPrivacyPolicyData = data.fromJson<UpdateApplicationPrivacyPolicyData>()
            updateApplicationPrivacyPolicy(
                applicationId = updateApplicationPrivacyPolicyData.applicationId,
                privacyPolicy = updateApplicationPrivacyPolicyData.privacyPolicy
            )
        }
    }

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return runBlocking { PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact) }
    }

    @DoordeckOnly
    @CName("updateApplicationSupportContactJson")
    fun updateApplicationSupportContactJson(data: String): String {
        return resultData {
            val updateApplicationSupportContactData = data.fromJson<UpdateApplicationSupportContactData>()
            updateApplicationSupportContact(
                applicationId = updateApplicationSupportContactData.applicationId,
                supportContact = updateApplicationSupportContactData.supportContact
            )
        }
    }

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return runBlocking { PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    @DoordeckOnly
    @CName("updateApplicationAppLinkJson")
    fun updateApplicationAppLinkJson(data: String): String {
        return resultData {
            val updateApplicationAppLinkData = data.fromJson<UpdateApplicationAppLinkData>()
            updateApplicationAppLink(
                applicationId = updateApplicationAppLinkData.applicationId,
                appLink = updateApplicationAppLinkData.appLink
            )
        }
    }

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return runBlocking { PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    @DoordeckOnly
    @CName("updateApplicationEmailPreferencesJson")
    fun updateApplicationEmailPreferencesJson(data: String): String {
        return resultData {
            val updateApplicationEmailPreferencesData = data.fromJson<UpdateApplicationEmailPreferencesData>()
            updateApplicationEmailPreferences(
                applicationId = updateApplicationEmailPreferencesData.applicationId,
                emailPreferences = updateApplicationEmailPreferencesData.emailPreferences.toEmailPreferences()
            )
        }
    }

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return runBlocking { PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    @DoordeckOnly
    @CName("updateApplicationLogoUrlJson")
    fun updateApplicationLogoUrlJson(data: String): String {
        return resultData {
            val updateApplicationLogoUrlData = data.fromJson<UpdateApplicationLogoUrlData>()
            updateApplicationLogoUrl(
                applicationId = updateApplicationLogoUrlData.applicationId,
                logoUrl = updateApplicationLogoUrlData.logoUrl
            )
        }
    }

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    fun deleteApplication(applicationId: String) {
        return runBlocking { PlatformClient.deleteApplicationRequest(applicationId) }
    }

    @DoordeckOnly
    @CName("deleteApplicationJson")
    fun deleteApplicationJson(data: String): String {
        return resultData {
            val deleteApplicationData = data.fromJson<DeleteApplicationData>()
            deleteApplication(deleteApplicationData.applicationId)
        }
    }

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return runBlocking { PlatformClient.getLogoUploadUrlRequest(applicationId, contentType) }
    }

    @DoordeckOnly
    @CName("getLogoUploadUrlJson")
    fun getLogoUploadUrlJson(data: String): String {
        return resultData {
            val getLogoUploadUrlData = data.fromJson<GetLogoUploadUrlData>()
            getLogoUploadUrl(getLogoUploadUrlData.applicationId, getLogoUploadUrlData.contentType)
        }
    }

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return runBlocking { PlatformClient.addAuthKeyRequest(applicationId, key) }
    }

    @DoordeckOnly
    @CName("addAuthKeyJson")
    fun addAuthKeyJson(data: String): String {
        return resultData {
            val addAuthKeyData = data.fromJson<AddAuthKeyData>()
            addAuthKey(addAuthKeyData.applicationId, addAuthKeyData.key.toAuthKey())
        }
    }

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    fun addAuthIssuer(applicationId: String, url: String) {
        return runBlocking { PlatformClient.addAuthIssuerRequest(applicationId, url) }
    }

    @DoordeckOnly
    @CName("addAuthIssuerJson")
    fun addAuthIssuerJson(data: String): String {
        return resultData {
            val addAuthIssuerData = data.fromJson<AddAuthIssuerData>()
            addAuthIssuer(addAuthIssuerData.applicationId, addAuthIssuerData.url)
        }
    }

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    fun deleteAuthIssuer(applicationId: String, url: String) {
        return runBlocking { PlatformClient.deleteAuthIssuerRequest(applicationId, url) }
    }

    @DoordeckOnly
    @CName("deleteAuthIssuerJson")
    fun deleteAuthIssuerJson(data: String): String {
        return resultData {
            val deleteAuthIssuerData = data.fromJson<DeleteAuthIssuerData>()
            deleteAuthIssuer(deleteAuthIssuerData.applicationId, deleteAuthIssuerData.url)
        }
    }

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    fun addCorsDomain(applicationId: String, url: String) {
        return runBlocking { PlatformClient.addCorsDomainRequest(applicationId, url) }
    }

    @DoordeckOnly
    @CName("addCorsDomainJson")
    fun addCorsDomainJson(data: String): String {
        return resultData {
            val addCorsDomainData = data.fromJson<AddCorsDomainData>()
            addCorsDomain(addCorsDomainData.applicationId, addCorsDomainData.url)
        }
    }

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    fun removeCorsDomain(applicationId: String, url: String) {
        return runBlocking { PlatformClient.removeCorsDomainRequest(applicationId, url) }
    }

    @DoordeckOnly
    @CName("removeCorsDomainJson")
    fun removeCorsDomainJson(data: String): String {
        return resultData {
            val removeCorsDomainData = data.fromJson<RemoveCorsDomainData>()
            removeCorsDomain(removeCorsDomainData.applicationId, removeCorsDomainData.url)
        }
    }

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    fun addApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { PlatformClient.addApplicationOwnerRequest(applicationId, userId) }
    }

    @DoordeckOnly
    @CName("addApplicationOwnerJson")
    fun addApplicationOwnerJson(data: String): String {
        return resultData {
            val addApplicationOwnerData = data.fromJson<AddApplicationOwnerData>()
            addApplicationOwner(addApplicationOwnerData.applicationId, addApplicationOwnerData.userId)
        }
    }

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    fun removeApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { PlatformClient.removeApplicationOwnerRequest(applicationId, userId) }
    }

    @DoordeckOnly
    @CName("removeApplicationOwnerJson")
    fun removeApplicationOwnerJson(data: String): String {
        return resultData {
            val removeApplicationOwnerData = data.fromJson<RemoveApplicationOwnerData>()
            removeApplicationOwner(
                applicationId = removeApplicationOwnerData.applicationId,
                userId = removeApplicationOwnerData.userId
            )
        }
    }

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return runBlocking { PlatformClient.getApplicationOwnersDetailsRequest(applicationId) }
    }

    @DoordeckOnly
    @CName("getApplicationOwnersDetailsJson")
    fun getApplicationOwnersDetailsJson(data: String): String {
        return resultData {
            val getApplicationOwnersDetailsData = data.fromJson<GetApplicationOwnersDetailsData>()
            getApplicationOwnersDetails(getApplicationOwnersDetailsData.applicationId)
        }
    }
}

actual fun platform(): PlatformApi = PlatformApi