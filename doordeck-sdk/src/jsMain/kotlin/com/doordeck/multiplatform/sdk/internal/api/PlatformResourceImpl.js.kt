package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal class PlatformResourceImpl(
    private val platformClient: PlatformClient
) : PlatformResource {

    override fun createApplication(application: Platform.CreateApplication): Promise<Unit> {
        return GlobalScope.promise { platformClient.createApplicationRequest(application) }
    }

    override fun listApplications(): Promise<List<ApplicationResponse>> {
        return GlobalScope.promise { platformClient.listApplicationsRequest() }
    }

    override fun getApplication(applicationId: String): Promise<ApplicationResponse> {
        return GlobalScope.promise { platformClient.getApplicationRequest(applicationId) }
    }

    override fun updateApplicationName(applicationId: String, name: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.updateApplicationNameRequest(applicationId, name) }
    }

    override fun updateApplicationCompanyName(applicationId: String, companyName: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override fun updateApplicationSupportContact(applicationId: String, supportContact: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.updateApplicationSupportContactRequest(applicationId, supportContact)}
    }

    override fun updateApplicationAppLink(applicationId: String, appLink: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences): Promise<Unit> {
        return GlobalScope.promise { platformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override fun updateApplicationLogoUrl(applicationId: String, logoUrl: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override fun deleteApplication(applicationId: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.deleteApplicationRequest(applicationId) }
    }

    override fun getLogoUploadUrl(applicationId: String, contentType: String): Promise<GetLogoUploadUrlResponse> {
        return GlobalScope.promise { platformClient.getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override fun addAuthKey(applicationId: String, key: Platform.AuthKey): Promise<Unit> {
        return GlobalScope.promise { platformClient.addAuthKeyRequest(applicationId, key) }
    }

    override fun addAuthIssuer(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.addAuthIssuerRequest(applicationId, url) }
    }

    override fun deleteAuthIssuer(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.deleteAuthIssuerRequest(applicationId, url) }
    }

    override fun addCorsDomain(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.addCorsDomainRequest(applicationId, url) }
    }

    override fun removeCorsDomain(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.removeCorsDomainRequest(applicationId, url) }
    }

    override fun addApplicationOwner(applicationId: String, userId: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.addApplicationOwnerRequest(applicationId, userId) }
    }

    override fun removeApplicationOwner(applicationId: String, userId: String): Promise<Unit> {
        return GlobalScope.promise { platformClient.removeApplicationOwnerRequest(applicationId, userId) }
    }

    override fun getApplicationOwnersDetails(applicationId: String): Promise<List<ApplicationOwnerDetailsResponse>> {
        return GlobalScope.promise { platformClient.getApplicationOwnersDetailsRequest(applicationId) }
    }
}