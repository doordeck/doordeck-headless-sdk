package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.PlatformResource
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class PlatformResourceImpl(
    httpClient: HttpClient
) : PlatformClient(httpClient), PlatformResource {

    override suspend fun createApplication(application: Platform.CreateApplication) {
        return createApplicationRequest(application)
    }

    override fun createApplicationAsync(application: Platform.CreateApplication): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { createApplicationRequest(application) }
    }

    override suspend fun listApplications(): List<ApplicationResponse> {
        return listApplicationsRequest()
    }

    override fun listApplicationsAsync(): CompletableFuture<List<ApplicationResponse>> {
        return GlobalScope.future(Dispatchers.IO) { listApplicationsRequest() }
    }

    override suspend fun getApplication(applicationId: String): ApplicationResponse {
        return getApplicationRequest(applicationId)
    }

    override fun getApplicationAsync(applicationId: String): CompletableFuture<ApplicationResponse> {
        return GlobalScope.future(Dispatchers.IO) { getApplicationRequest(applicationId) }
    }

    override suspend fun updateApplicationName(applicationId: String, name: String) {
        return updateApplicationNameRequest(applicationId, name)
    }

    override fun updateApplicationNameAsync(applicationId: String, name: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateApplicationNameRequest(applicationId, name) }
    }

    override suspend fun updateApplicationCompanyName(applicationId: String, companyName: String) {
        return updateApplicationCompanyNameRequest(applicationId, companyName)
    }

    override fun updateApplicationCompanyNameAsync(applicationId: String, companyName: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateApplicationCompanyNameRequest(applicationId, companyName) }
    }

    override suspend fun updateApplicationMailingAddress(applicationId: String, mailingAddress: String) {
        return updateApplicationMailingAddressRequest(applicationId, mailingAddress)
    }

    override fun updateApplicationMailingAddressAsync(applicationId: String, mailingAddress: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateApplicationMailingAddressRequest(applicationId, mailingAddress) }
    }

    override suspend fun updateApplicationPrivacyPolicy(applicationId: String, privacyPolicy: String) {
        return updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy)
    }

    override fun updateApplicationPrivacyPolicyAsync(applicationId: String, privacyPolicy: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateApplicationPrivacyPolicyRequest(applicationId, privacyPolicy) }
    }

    override suspend fun updateApplicationSupportContact(applicationId: String, supportContact: String) {
        return updateApplicationSupportContactRequest(applicationId, supportContact)
    }

    override fun updateApplicationSupportContactAsync(applicationId: String, supportContact: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateApplicationSupportContactRequest(applicationId, supportContact) }
    }

    override suspend fun updateApplicationAppLink(applicationId: String, appLink: String) {
        return updateApplicationAppLinkRequest(applicationId, appLink)
    }

    override fun updateApplicationAppLinkAsync(applicationId: String, appLink: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateApplicationAppLinkRequest(applicationId, appLink) }
    }

    override suspend fun updateApplicationEmailPreferences(applicationId: String, emailPreferences: Platform.EmailPreferences) {
        return updateApplicationEmailPreferencesRequest(applicationId, emailPreferences)
    }

    override fun updateApplicationEmailPreferencesAsync(applicationId: String, emailPreferences: Platform.EmailPreferences): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateApplicationEmailPreferencesRequest(applicationId, emailPreferences) }
    }

    override suspend fun updateApplicationLogoUrl(applicationId: String, logoUrl: String) {
        return updateApplicationLogoUrlRequest(applicationId, logoUrl)
    }

    override fun updateApplicationLogoUrlAsync(applicationId: String, logoUrl: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateApplicationLogoUrlRequest(applicationId, logoUrl) }
    }

    override suspend fun deleteApplication(applicationId: String) {
        return deleteApplicationRequest(applicationId)
    }

    override fun deleteApplicationAsync(applicationId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { deleteApplicationRequest(applicationId) }
    }

    override suspend fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return getLogoUploadUrlRequest(applicationId, contentType)
    }

    override fun getLogoUploadUrlAsync(applicationId: String, contentType: String): CompletableFuture<GetLogoUploadUrlResponse> {
        return GlobalScope.future(Dispatchers.IO) { getLogoUploadUrlRequest(applicationId, contentType) }
    }

    override suspend fun addAuthKey(applicationId: String, key: Platform.AuthKey) {
        return addAuthKeyRequest(applicationId, key)
    }

    override fun addAuthKeyAsync(applicationId: String, key: Platform.AuthKey): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { addAuthKeyRequest(applicationId, key) }
    }

    override suspend fun addAuthIssuer(applicationId: String, url: String) {
        return addAuthIssuerRequest(applicationId, url)
    }

    override fun addAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { addAuthIssuerRequest(applicationId, url) }
    }

    override suspend fun deleteAuthIssuer(applicationId: String, url: String) {
        return deleteAuthIssuerRequest(applicationId, url)
    }

    override fun deleteAuthIssuerAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { deleteAuthIssuerRequest(applicationId, url) }
    }

    override suspend fun addCorsDomain(applicationId: String, url: String) {
        return addCorsDomainRequest(applicationId, url)
    }

    override fun addCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { addCorsDomainRequest(applicationId, url) }
    }

    override suspend fun removeCorsDomain(applicationId: String, url: String) {
        return removeCorsDomainRequest(applicationId, url)
    }

    override fun removeCorsDomainAsync(applicationId: String, url: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) {removeCorsDomainRequest(applicationId, url) }
    }

    override suspend fun addApplicationOwner(applicationId: String, userId: String) {
        return addApplicationOwnerRequest(applicationId, userId)
    }

    override fun addApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { addApplicationOwnerRequest(applicationId, userId) }
    }

    override suspend fun removeApplicationOwner(applicationId: String, userId: String) {
        return removeApplicationOwnerRequest(applicationId, userId)
    }

    override fun removeApplicationOwnerAsync(applicationId: String, userId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { removeApplicationOwnerRequest(applicationId, userId) }
    }

    override suspend fun getApplicationOwnersDetails(applicationId: String): List<ApplicationOwnerDetailsResponse> {
        return getApplicationOwnersDetailsRequest(applicationId)
    }

    override fun getApplicationOwnersDetailsAsync(applicationId: String): CompletableFuture<List<ApplicationOwnerDetailsResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getApplicationOwnersDetailsRequest(applicationId) }
    }
}