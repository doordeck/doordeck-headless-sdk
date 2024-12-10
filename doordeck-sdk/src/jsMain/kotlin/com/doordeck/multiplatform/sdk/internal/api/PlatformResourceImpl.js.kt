package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal object PlatformResourceImpl : PlatformResource {

    override fun createApplication(application: Platform.CreateApplication): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.createApplicationRequest(application) }
    }

    override fun listApplications(): Promise<List<ApplicationResponse>> {
        return GlobalScope.promise { PlatformClient.listApplicationsRequest() }
    }

    override fun getApplication(applicationId: String): Promise<ApplicationResponse> {
        return GlobalScope.promise { PlatformClient.getApplicationRequest(applicationId) }
    }

    override fun updateApplicationName(applicationId: String, name: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.updateApplicationNameRequest(applicationId, name) }
    }

    override fun updateApplicationCompanyName(applicationId: String, companyName: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override fun updateApplicationSupportContact(applicationId: String, supportContact: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact)}
    }

    override fun updateApplicationAppLink(applicationId: String, appLink: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override fun updateApplicationLogoUrl(applicationId: String, logoUrl: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override fun deleteApplication(applicationId: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.deleteApplicationRequest(applicationId) }
    }

    override fun getLogoUploadUrl(applicationId: String, contentType: String): Promise<GetLogoUploadUrlResponse> {
        return GlobalScope.promise { PlatformClient.getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override fun addAuthKey(applicationId: String, key: Platform.AuthKey): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.addAuthKeyRequest(applicationId, key) }
    }

    override fun addAuthIssuer(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.addAuthIssuerRequest(applicationId, url) }
    }

    override fun deleteAuthIssuer(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.deleteAuthIssuerRequest(applicationId, url) }
    }

    override fun addCorsDomain(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.addCorsDomainRequest(applicationId, url) }
    }

    override fun removeCorsDomain(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.removeCorsDomainRequest(applicationId, url) }
    }

    override fun addApplicationOwner(applicationId: String, userId: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.addApplicationOwnerRequest(applicationId, userId) }
    }

    override fun removeApplicationOwner(applicationId: String, userId: String): Promise<Unit> {
        return GlobalScope.promise { PlatformClient.removeApplicationOwnerRequest(applicationId, userId) }
    }

    override fun getApplicationOwnersDetails(applicationId: String): Promise<List<ApplicationOwnerDetailsResponse>> {
        return GlobalScope.promise { PlatformClient.getApplicationOwnersDetailsRequest(applicationId) }
    }
}