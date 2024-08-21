package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import io.ktor.client.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

class PlatformResourceImpl(
    private val httpClient: HttpClient
) : AbstractPlatformClientImpl(httpClient), PlatformResource {

    override fun createApplication(application: Platform.CreateApplication): Promise<Unit> {
        return GlobalScope.promise { createApplicationRequest(application) }
    }

    override fun listApplications(): Promise<Array<ApplicationResponse>> {
        return GlobalScope.promise { listApplicationsRequest() }
    }

    override fun getApplication(applicationId: String): Promise<ApplicationResponse> {
        return GlobalScope.promise { getApplicationRequest(applicationId) }
    }

    override fun updateApplicationName(applicationId: String, name: String): Promise<Unit> {
        return GlobalScope.promise { updateApplicationNameRequest(applicationId, name) }
    }

    override fun updateApplicationCompanyName(applicationId: String, companyName: String): Promise<Unit> {
        return GlobalScope.promise { updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String): Promise<Unit> {
        return GlobalScope.promise { updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String): Promise<Unit> {
        return GlobalScope.promise { updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override fun updateApplicationSupportContact(applicationId: String, supportContact: String): Promise<Unit> {
        return GlobalScope.promise { updateApplicationSupportContactRequest(applicationId, supportContact)}
    }

    override fun updateApplicationAppLink(applicationId: String, appLink: String): Promise<Unit> {
        return GlobalScope.promise { updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences): Promise<Unit> {
        return GlobalScope.promise { updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override fun updateApplicationLogoUrl(applicationId: String, logoUrl: String): Promise<Unit> {
        return GlobalScope.promise { updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override fun deleteApplication(applicationId: String): Promise<Unit> {
        return GlobalScope.promise { deleteApplicationRequest(applicationId) }
    }

    override fun getLogoUploadUrl(applicationId: String, contentType: String): Promise<GetLogoUploadUrlResponse> {
        return GlobalScope.promise { getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override fun addAuthKey(applicationId: String, key: Platform.AuthKey): Promise<Unit> {
        return GlobalScope.promise { addAuthKeyRequest(applicationId, key) }
    }

    override fun addAuthIssuer(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { addAuthIssuerRequest(applicationId, url) }
    }

    override fun deleteAuthIssuer(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { deleteAuthIssuerRequest(applicationId, url) }
    }

    override fun addCorsDomain(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { addCorsDomainRequest(applicationId, url) }
    }

    override fun removeCorsDomain(applicationId: String, url: String): Promise<Unit> {
        return GlobalScope.promise { removeCorsDomainRequest(applicationId, url) }
    }

    override fun addApplicationOwner(applicationId: String, userId: String): Promise<Unit> {
        return GlobalScope.promise { addApplicationOwnerRequest(applicationId, userId) }
    }

    override fun removeApplicationOwner(applicationId: String, userId: String): Promise<Unit> {
        return GlobalScope.promise { removeApplicationOwnerRequest(applicationId, userId) }
    }

    override fun getApplicationOwnersDetails(applicationId: String): Promise<Array<ApplicationOwnerDetailsResponse>> {
        return GlobalScope.promise { getApplicationOwnersDetailsRequest(applicationId) }
    }
}