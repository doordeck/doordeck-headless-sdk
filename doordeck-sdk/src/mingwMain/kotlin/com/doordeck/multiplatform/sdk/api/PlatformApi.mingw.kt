package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.PlatformClient
import com.doordeck.multiplatform.sdk.model.data.AddAuthKeyData
import com.doordeck.multiplatform.sdk.model.data.ApplicationIdData
import com.doordeck.multiplatform.sdk.model.data.ApplicationOwnerData
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
import com.doordeck.multiplatform.sdk.util.launchCallback
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef

actual object PlatformApi {
    /**
     * Create application
     *
     * @see <a href="https://developer.doordeck.com/docs/#create-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("createApplication")
    fun createApplication(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val createApplicationData = data.fromJson<CreateApplicationData>()
                PlatformClient.createApplicationRequest(createApplicationData.toCreateApplication())
            },
            callback = callback
        )
    }

    /**
     * List applications
     *
     * @see <a href="https://developer.doordeck.com/docs/#list-applications">API Doc</a>
     */
    @DoordeckOnly
    @CName("listApplications")
    fun listApplications(callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                PlatformClient.listApplicationsRequest()
            },
            callback = callback
        )
    }

    /**
     * Get application
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("getApplication")
    fun getApplication(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val applicationIdData = data.fromJson<ApplicationIdData>()
                PlatformClient.getApplicationRequest(applicationIdData.applicationId)
            },
            callback = callback
        )
    }

    /**
     * Update application - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("updateApplicationName")
    fun updateApplicationName(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val updateApplicationName = data.fromJson<UpdateApplicationNameData>()
                PlatformClient.updateApplicationNameRequest(updateApplicationName.applicationId, updateApplicationName.name)
            },
            callback = callback
        )
    }

    /**
     * Update application - Company name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("updateApplicationCompanyName")
    fun updateApplicationCompanyName(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val updateApplicationCompanyName = data.fromJson<UpdateApplicationCompanyNameData>()
                PlatformClient.updateApplicationCompanyNameRequest(
                    applicationId = updateApplicationCompanyName.applicationId,
                    companyName = updateApplicationCompanyName.companyName
                )
            },
            callback = callback
        )
    }

    /**
     * Update application - Mailing address
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("updateApplicationMailingAddress")
    fun updateApplicationMailingAddress(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val updateApplicationMailingAddressData = data.fromJson<UpdateApplicationMailingAddressData>()
                PlatformClient.updateApplicationMailingAddressRequest(
                    applicationId = updateApplicationMailingAddressData.applicationId,
                    mailingAddress = updateApplicationMailingAddressData.mailingAddress
                )
            },
            callback = callback
        )
    }

    /**
     * Update application - Privacy policy
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("updateApplicationPrivacyPolicy")
    fun updateApplicationPrivacyPolicy(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val updateApplicationPrivacyPolicyData = data.fromJson<UpdateApplicationPrivacyPolicyData>()
                PlatformClient.updateApplicationPrivacyPolicyRequest(
                    applicationId = updateApplicationPrivacyPolicyData.applicationId,
                    privacyPolicy = updateApplicationPrivacyPolicyData.privacyPolicy
                )
            },
            callback = callback
        )
    }

    /**
     * Update application - Support contact
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("updateApplicationSupportContact")
    fun updateApplicationSupportContact(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val updateApplicationSupportContactData = data.fromJson<UpdateApplicationSupportContactData>()
                PlatformClient.updateApplicationSupportContactRequest(
                    applicationId = updateApplicationSupportContactData.applicationId,
                    supportContact = updateApplicationSupportContactData.supportContact
                )
            },
            callback = callback
        )
    }

    /**
     * Update application - App link
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("updateApplicationAppLink")
    fun updateApplicationAppLink(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val updateApplicationAppLinkData = data.fromJson<UpdateApplicationAppLinkData>()
                PlatformClient.updateApplicationAppLinkRequest(
                    applicationId = updateApplicationAppLinkData.applicationId,
                    appLink = updateApplicationAppLinkData.appLink
                )
            },
            callback = callback
        )
    }

    /**
     * Update application - Email preferences
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("updateApplicationEmailPreferences")
    fun updateApplicationEmailPreferences(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val updateApplicationEmailPreferencesData = data.fromJson<UpdateApplicationEmailPreferencesData>()
                PlatformClient.updateApplicationEmailPreferencesRequest(
                    applicationId = updateApplicationEmailPreferencesData.applicationId,
                    emailPreferences = updateApplicationEmailPreferencesData.emailPreferences.toEmailPreferences()
                )
            },
            callback = callback
        )
    }

    /**
     * Update application - Logo url
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("updateApplicationLogoUrl")
    fun updateApplicationLogoUrl(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val updateApplicationLogoUrlData = data.fromJson<UpdateApplicationLogoUrlData>()
                PlatformClient.updateApplicationLogoUrlRequest(
                    applicationId = updateApplicationLogoUrlData.applicationId,
                    logoUrl = updateApplicationLogoUrlData.logoUrl
                )
            },
            callback = callback
        )
    }

    /**
     * Delete application
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-application">API Doc</a>
     */
    @DoordeckOnly
    @CName("deleteApplication")
    fun deleteApplication(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val applicationIdData = data.fromJson<ApplicationIdData>()
                PlatformClient.deleteApplicationRequest(applicationIdData.applicationId)
            },
            callback = callback
        )
    }

    /**
     * Get logo upload URL
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-logo-upload-url">API Doc</a>
     */
    @DoordeckOnly
    @CName("getLogoUploadUrl")
    fun getLogoUploadUrl(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val getLogoUploadUrlData = data.fromJson<GetLogoUploadUrlData>()
                PlatformClient.getLogoUploadUrlRequest(getLogoUploadUrlData.applicationId, getLogoUploadUrlData.contentType)
            },
            callback = callback
        )
    }

    /**
     * Add auth key
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-key">API Doc</a>
     */
    @DoordeckOnly
    @CName("addAuthKey")
    fun addAuthKey(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val addAuthKeyData = data.fromJson<AddAuthKeyData>()
                PlatformClient.addAuthKeyRequest(addAuthKeyData.applicationId, addAuthKeyData.key.toAuthKey())
            },
            callback = callback
        )
    }

    /**
     * Add auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    @CName("addAuthIssuer")
    fun addAuthIssuer(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val authIssuerData = data.fromJson<AuthIssuerData>()
                PlatformClient.addAuthIssuerRequest(authIssuerData.applicationId, authIssuerData.url)
            },
            callback = callback
        )
    }

    /**
     * Delete auth issuer
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-auth-issuer">API Doc</a>
     */
    @DoordeckOnly
    @CName("deleteAuthIssuer")
    fun deleteAuthIssuer(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val authIssuerData = data.fromJson<AuthIssuerData>()
                PlatformClient.deleteAuthIssuerRequest(authIssuerData.applicationId, authIssuerData.url)
            },
            callback = callback
        )
    }

    /**
     * Add CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    @CName("addCorsDomain")
    fun addCorsDomain(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val corsDomainData = data.fromJson<CorsDomainData>()
                PlatformClient.addCorsDomainRequest(corsDomainData.applicationId, corsDomainData.url)
            },
            callback = callback
        )
    }

    /**
     * Remove CORS domain
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-cors-domain">API Doc</a>
     */
    @DoordeckOnly
    @CName("removeCorsDomain")
    fun removeCorsDomain(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val corsDomainData = data.fromJson<CorsDomainData>()
                PlatformClient.removeCorsDomainRequest(corsDomainData.applicationId, corsDomainData.url)
            },
            callback = callback
        )
    }

    /**
     * Add application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#add-application-owner">API Doc</a>
     */
    @DoordeckOnly
    @CName("addApplicationOwner")
    fun addApplicationOwner(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val applicationOwnerData = data.fromJson<ApplicationOwnerData>()
                PlatformClient.addApplicationOwnerRequest(
                    applicationId = applicationOwnerData.applicationId,
                    userId = applicationOwnerData.userId
                )
            },
            callback = callback
        )
    }

    /**
     * Remove application owner
     *
     * @see <a href="https://developer.doordeck.com/docs/#remove-application-owner">API Doc</a>
     */
    @DoordeckOnly
    @CName("removeApplicationOwner")
    fun removeApplicationOwner(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val applicationOwnerData = data.fromJson<ApplicationOwnerData>()
                PlatformClient.removeApplicationOwnerRequest(
                    applicationId = applicationOwnerData.applicationId,
                    userId = applicationOwnerData.userId
                )
            },
            callback = callback
        )
    }

    /**
     * Get application owners details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-application-owners-details">API Doc</a>
     */
    @DoordeckOnly
    @CName("getApplicationOwnersDetails")
    fun getApplicationOwnersDetails(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val applicationIdData = data.fromJson<ApplicationIdData>()
                PlatformClient.getApplicationOwnersDetailsRequest(applicationIdData.applicationId)
            },
            callback = callback
        )
    }
}

actual fun platform(): PlatformApi = PlatformApi