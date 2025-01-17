package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.AddApplicationOwnerData
import com.doordeck.multiplatform.sdk.api.model.AddAuthIssuerData
import com.doordeck.multiplatform.sdk.api.model.AddAuthKeyData
import com.doordeck.multiplatform.sdk.api.model.AddCorsDomainData
import com.doordeck.multiplatform.sdk.api.model.CreateApplicationData
import com.doordeck.multiplatform.sdk.api.model.DeleteApplicationData
import com.doordeck.multiplatform.sdk.api.model.DeleteAuthIssuerData
import com.doordeck.multiplatform.sdk.api.model.GetApplicationData
import com.doordeck.multiplatform.sdk.api.model.GetApplicationOwnersDetailsData
import com.doordeck.multiplatform.sdk.api.model.GetLogoUploadUrlData
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.model.RemoveApplicationOwnerData
import com.doordeck.multiplatform.sdk.api.model.RemoveCorsDomainData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationAppLinkData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationCompanyNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationEmailPreferencesData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationLogoUrlData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationMailingAddressData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationPrivacyPolicyData
import com.doordeck.multiplatform.sdk.api.model.UpdateApplicationSupportContactData
import com.doordeck.multiplatform.sdk.api.model.toAuthKey
import com.doordeck.multiplatform.sdk.api.model.toCreateApplication
import com.doordeck.multiplatform.sdk.api.model.toEmailPreferences
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

internal object PlatformResourceImpl : PlatformResource {

    override fun createApplication(application: Platform.CreateApplication) {
        return runBlocking { PlatformClient.createApplicationRequest(application) }
    }

    override fun createApplicationJson(data: String): String {
        return resultData {
            val createApplicationData = data.fromJson<CreateApplicationData>()
            createApplication(createApplicationData.toCreateApplication())
        }
    }

    override fun listApplications(): List<ApplicationResponse> {
        return runBlocking { PlatformClient.listApplicationsRequest() }
    }

    override fun listApplicationsJson(): String {
        return resultData {
            listApplications()
        }
    }

    override fun getApplication(applicationId: String): ApplicationResponse {
        return runBlocking { PlatformClient.getApplicationRequest(applicationId) }
    }

    override fun getApplicationJson(data: String): String {
        return resultData {
            val getApplicationData = data.fromJson<GetApplicationData>()
            getApplication(getApplicationData.applicationId)
        }
    }

    override fun updateApplicationName(applicationId: String, name: String) {
        return runBlocking { PlatformClient.updateApplicationNameRequest(applicationId, name) }
    }

    override fun updateApplicationNameJson(data: String): String {
        return resultData {
            val updateApplicationName = data.fromJson<UpdateApplicationNameData>()
            updateApplicationName(updateApplicationName.applicationId, updateApplicationName.name)
        }
    }

    override fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return runBlocking { PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override fun updateApplicationCompanyNameJson(data: String): String {
        return resultData {
            val updateApplicationCompanyName = data.fromJson<UpdateApplicationCompanyNameData>()
            updateApplicationCompanyName(updateApplicationCompanyName.applicationId, updateApplicationCompanyName.companyName)
        }
    }

    override fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return runBlocking { PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override fun updateApplicationMailingAddressJson(data: String): String {
        return resultData {
            val updateApplicationMailingAddressData = data.fromJson<UpdateApplicationMailingAddressData>()
            updateApplicationMailingAddress(updateApplicationMailingAddressData.applicationId, updateApplicationMailingAddressData.mailingAddress)
        }
    }

    override fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return runBlocking { PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override fun updateApplicationPrivacyPolicyJson(data: String): String {
        return resultData {
            val updateApplicationPrivacyPolicyData = data.fromJson<UpdateApplicationPrivacyPolicyData>()
            updateApplicationPrivacyPolicy(updateApplicationPrivacyPolicyData.applicationId, updateApplicationPrivacyPolicyData.privacyPolicy)
        }
    }

    override fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return runBlocking { PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact) }
    }

    override fun updateApplicationSupportContactJson(data: String): String {
        return resultData {
            val updateApplicationSupportContactData = data.fromJson<UpdateApplicationSupportContactData>()
            updateApplicationSupportContact(updateApplicationSupportContactData.applicationId, updateApplicationSupportContactData.supportContact)
        }
    }

    override fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return runBlocking { PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override fun updateApplicationAppLinkJson(data: String): String {
        return resultData {
            val updateApplicationAppLinkData = data.fromJson<UpdateApplicationAppLinkData>()
            updateApplicationAppLink(updateApplicationAppLinkData.applicationId, updateApplicationAppLinkData.appLink)
        }
    }

    override fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return runBlocking { PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override fun updateApplicationEmailPreferencesJson(data: String): String {
        return resultData {
            val updateApplicationEmailPreferencesData = data.fromJson<UpdateApplicationEmailPreferencesData>()
            updateApplicationEmailPreferences(updateApplicationEmailPreferencesData.applicationId, updateApplicationEmailPreferencesData.emailPreferences.toEmailPreferences())
        }
    }

    override fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return runBlocking { PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override fun updateApplicationLogoUrlJson(data: String): String {
        return resultData {
            val updateApplicationLogoUrlData = data.fromJson<UpdateApplicationLogoUrlData>()
            updateApplicationLogoUrl(updateApplicationLogoUrlData.applicationId, updateApplicationLogoUrlData.logoUrl)
        }
    }

    override fun deleteApplication(applicationId: String) {
        return runBlocking { PlatformClient.deleteApplicationRequest(applicationId) }
    }

    override fun deleteApplicationJson(data: String): String {
        return resultData {
            val deleteApplicationData = data.fromJson<DeleteApplicationData>()
            deleteApplication(deleteApplicationData.applicationId)
        }
    }

    override fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return runBlocking { PlatformClient.getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override fun getLogoUploadUrlJson(data: String): String {
        return resultData {
            val getLogoUploadUrlData = data.fromJson<GetLogoUploadUrlData>()
            getLogoUploadUrl(getLogoUploadUrlData.applicationId, getLogoUploadUrlData.contentType)
        }
    }

    override fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return runBlocking { PlatformClient.addAuthKeyRequest(applicationId, key) }
    }

    override fun addAuthKeyJson(data: String): String {
        return resultData {
            val addAuthKeyData = data.fromJson<AddAuthKeyData>()
            addAuthKey(addAuthKeyData.applicationId, addAuthKeyData.key.toAuthKey())
        }
    }

    override fun addAuthIssuer(applicationId: String, url: String) {
        return runBlocking { PlatformClient.addAuthIssuerRequest(applicationId, url) }
    }

    override fun addAuthIssuerJson(data: String): String {
        return resultData {
            val addAuthIssuerData = data.fromJson<AddAuthIssuerData>()
            addAuthIssuer(addAuthIssuerData.applicationId, addAuthIssuerData.url)
        }
    }

    override fun deleteAuthIssuer(applicationId: String, url: String) {
        return runBlocking { PlatformClient.deleteAuthIssuerRequest(applicationId, url) }
    }

    override fun deleteAuthIssuerJson(data: String): String {
        return resultData {
            val deleteAuthIssuerData = data.fromJson<DeleteAuthIssuerData>()
            deleteAuthIssuer(deleteAuthIssuerData.applicationId, deleteAuthIssuerData.url)
        }
    }

    override fun addCorsDomain(applicationId: String, url: String) {
        return runBlocking { PlatformClient.addCorsDomainRequest(applicationId, url) }
    }

    override fun addCorsDomainJson(data: String): String {
        return resultData {
            val addCorsDomainData = data.fromJson<AddCorsDomainData>()
            addCorsDomain(addCorsDomainData.applicationId, addCorsDomainData.url)
        }
    }

    override fun removeCorsDomain(applicationId: String, url: String) {
        return runBlocking { PlatformClient.removeCorsDomainRequest(applicationId, url) }
    }

    override fun removeCorsDomainJson(data: String): String {
        return resultData {
            val removeCorsDomainData = data.fromJson<RemoveCorsDomainData>()
            removeCorsDomain(removeCorsDomainData.applicationId, removeCorsDomainData.url)
        }
    }

    override fun addApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { PlatformClient.addApplicationOwnerRequest(applicationId, userId) }
    }

    override fun addApplicationOwnerJson(data: String): String {
        return resultData {
            val addApplicationOwnerData = data.fromJson<AddApplicationOwnerData>()
            addApplicationOwner(addApplicationOwnerData.applicationId, addApplicationOwnerData.userId)
        }
    }

    override fun removeApplicationOwner(applicationId: String, userId: String) {
        return runBlocking { PlatformClient.removeApplicationOwnerRequest(applicationId, userId) }
    }

    override fun removeApplicationOwnerJson(data: String): String {
        return resultData {
            val removeApplicationOwnerData = data.fromJson<RemoveApplicationOwnerData>()
            removeApplicationOwner(removeApplicationOwnerData.applicationId, removeApplicationOwnerData.userId)
        }
    }

    override fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return runBlocking { PlatformClient.getApplicationOwnersDetailsRequest(applicationId) }
    }

    override fun getApplicationOwnersDetailsJson(data: String): String {
        return resultData {
            val getApplicationOwnersDetailsData = data.fromJson<GetApplicationOwnersDetailsData>()
            getApplicationOwnersDetails(getApplicationOwnersDetailsData.applicationId)
        }
    }
}