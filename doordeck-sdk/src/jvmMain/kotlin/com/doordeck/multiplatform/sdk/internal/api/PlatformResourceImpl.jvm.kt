package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal object PlatformResourceImpl : PlatformResource {

    override suspend fun createApplication(application: Platform.CreateApplication) {
        return PlatformClient.createApplicationRequest(application)
    }

    override fun createApplicationAsync(application: Platform.CreateApplication): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.createApplicationRequest(application) }
    }

    override suspend fun listApplications(): List<ApplicationResponse> {
        return PlatformClient.listApplicationsRequest()
    }

    override fun listApplicationsAsync(): CompletableFuture<List<ApplicationResponse>> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.listApplicationsRequest() }
    }

    override suspend fun getApplication(applicationId: String): ApplicationResponse {
        return PlatformClient.getApplicationRequest(applicationId)
    }

    override fun getApplicationAsync(applicationId: String): CompletableFuture<ApplicationResponse> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.getApplicationRequest(applicationId) }
    }

    override suspend fun updateApplicationName(applicationId: String, name: String) {
        return PlatformClient.updateApplicationNameRequest(applicationId, name)
    }

    override fun updateApplicationNameAsync(applicationId: String, name: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.updateApplicationNameRequest(applicationId, name) }
    }

    override suspend fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName)
    }

    override fun updateApplicationCompanyNameAsync(applicationId: String, companyName: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress)
    }

    override fun updateApplicationMailingAddressAsync(applicationId: String, mailingAddress: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy)
    }

    override fun updateApplicationPrivacyPolicyAsync(applicationId: String, privacyPolicy: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact)
    }

    override fun updateApplicationSupportContactAsync(applicationId: String, supportContact: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.updateApplicationSupportContactRequest(applicationId, supportContact) }
    }

    override suspend fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink)
    }

    override fun updateApplicationAppLinkAsync(applicationId: String, appLink: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences)
    }

    override fun updateApplicationEmailPreferencesAsync(applicationId: String, emailPreferences: Platform.EmailPreferences): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl)
    }

    override fun updateApplicationLogoUrlAsync(applicationId: String, logoUrl: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override suspend fun deleteApplication(applicationId: String) {
        return PlatformClient.deleteApplicationRequest(applicationId)
    }

    override fun deleteApplicationAsync(applicationId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.deleteApplicationRequest(applicationId) }
    }

    override suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return PlatformClient.getLogoUploadUrlRequest(applicationId, contentType)
    }

    override fun getLogoUploadUrlAsync(applicationId: String, contentType: String): CompletableFuture<GetLogoUploadUrlResponse> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override suspend fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return PlatformClient.addAuthKeyRequest(applicationId, key)
    }

    override fun addAuthKeyAsync(applicationId: String, key: Platform.AuthKey): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.addAuthKeyRequest(applicationId, key) }
    }

    override suspend fun addAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.addAuthIssuerRequest(applicationId, url)
    }

    override fun addAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.addAuthIssuerRequest(applicationId, url) }
    }

    override suspend fun deleteAuthIssuer(applicationId: String, url: String) {
        return PlatformClient.deleteAuthIssuerRequest(applicationId, url)
    }

    override fun deleteAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.deleteAuthIssuerRequest(applicationId, url) }
    }

    override suspend fun addCorsDomain(applicationId: String, url: String) {
        return PlatformClient.addCorsDomainRequest(applicationId, url)
    }

    override fun addCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.addCorsDomainRequest(applicationId, url) }
    }

    override suspend fun removeCorsDomain(applicationId: String, url: String) {
        return PlatformClient.removeCorsDomainRequest(applicationId, url)
    }

    override fun removeCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.removeCorsDomainRequest(applicationId, url) }
    }

    override suspend fun addApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.addApplicationOwnerRequest(applicationId, userId)
    }

    override fun addApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.addApplicationOwnerRequest(applicationId, userId) }
    }

    override suspend fun removeApplicationOwner(applicationId: String, userId: String) {
        return PlatformClient.removeApplicationOwnerRequest(applicationId, userId)
    }

    override fun removeApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.removeApplicationOwnerRequest(applicationId, userId) }
    }

    override suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return PlatformClient.getApplicationOwnersDetailsRequest(applicationId)
    }

    override fun getApplicationOwnersDetailsAsync(applicationId: String): CompletableFuture<List<ApplicationOwnerDetailsResponse>> {
        return GlobalScope.future(Dispatchers.IO) { PlatformClient.getApplicationOwnersDetailsRequest(applicationId) }
    }
}