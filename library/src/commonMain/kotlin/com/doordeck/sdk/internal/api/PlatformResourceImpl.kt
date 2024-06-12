package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.PlatformResource
import com.doordeck.sdk.api.requests.AddApplicationOwnerRequest
import com.doordeck.sdk.api.requests.AddAuthIssuerRequest
import com.doordeck.sdk.api.requests.AddCorsDomainRequest
import com.doordeck.sdk.api.requests.DeleteAuthIssuerRequest
import com.doordeck.sdk.api.requests.RemoveApplicationOwnerRequest
import com.doordeck.sdk.api.requests.RemoveCorsDomainRequest
import com.doordeck.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.sdk.api.responses.EmptyResponse
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class PlatformResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), PlatformResource {

    override fun createApplication() {
        TODO("Not yet implemented")
    }

    override fun listApplications() {
        TODO("Not yet implemented")
    }

    override fun getApplication(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun updateApplication(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun deleteApplication(applicationId: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getDeleteApplicationPath(applicationId))
    }

    override fun getLogoUploadUrl(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun addAuthKey(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun addAuthIssuer(applicationId: String, url: String): EmptyResponse {
        return httpClient.postEmpty(Paths.getAddAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddAuthIssuerRequest(url))
        }
    }

    override fun deleteAuthIssuer(applicationId: String, url: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getDeleteAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(DeleteAuthIssuerRequest(url))
        }
    }

    override fun addCorsDomain(applicationId: String, url: String): EmptyResponse {
        return httpClient.postEmpty(Paths.getAddCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(AddCorsDomainRequest(url))
        }
    }

    override fun removeCorsDomain(applicationId: String, url: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getRemoveCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveCorsDomainRequest(url))
        }
    }

    override fun addApplicationOwner(applicationId: String, userId: String): EmptyResponse {
        return httpClient.postEmpty(Paths.getAddApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddApplicationOwnerRequest(userId))
        }
    }

    override fun removeApplicationOwner(applicationId: String, userId: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getRemoveApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveApplicationOwnerRequest(userId))
        }
    }

    override fun getApplicationOwnersDetails(applicationId: String): ApplicationOwnerDetailsResponse {
        return httpClient.get(Paths.getApplicationOwnersDetailsPath(applicationId))
    }
}