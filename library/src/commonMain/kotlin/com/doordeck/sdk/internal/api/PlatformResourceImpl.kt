package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.PlatformResource
import com.doordeck.sdk.api.requests.AddApplicationOwnerRequest
import com.doordeck.sdk.api.requests.AddAuthIssuerRequest
import com.doordeck.sdk.api.requests.AddCorsDomainRequest
import com.doordeck.sdk.api.requests.DeleteAuthIssuerRequest
import com.doordeck.sdk.api.requests.GetLogoUploadUrlRequest
import com.doordeck.sdk.api.requests.RemoveApplicationOwnerRequest
import com.doordeck.sdk.api.requests.RemoveCorsDomainRequest
import com.doordeck.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.sdk.api.responses.EmptyResponse
import com.doordeck.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class PlatformResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), PlatformResource {

    @DoordeckOnly
    override fun createApplication() {
        TODO("Not yet implemented")
    }

    @DoordeckOnly
    override fun listApplications() {
        TODO("Not yet implemented")
    }

    @DoordeckOnly
    override fun getApplication(applicationId: String) {
        TODO("Not yet implemented")
    }

    @DoordeckOnly
    override fun updateApplication(applicationId: String) {
        TODO("Not yet implemented")
    }

    @DoordeckOnly
    override fun deleteApplication(applicationId: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getDeleteApplicationPath(applicationId))
    }

    @DoordeckOnly
    override fun getLogoUploadUrl(applicationId: String, contentType: String): GetLogoUploadUrlResponse {
        return httpClient.post(Paths.getLogoUploadUrlPath(applicationId)) {
            addRequestHeaders()
            setBody(GetLogoUploadUrlRequest(contentType))
        }
    }

    @DoordeckOnly
    override fun addAuthKey(applicationId: String) {
        TODO("Not yet implemented")
    }

    @DoordeckOnly
    override fun addAuthIssuer(applicationId: String, url: String): EmptyResponse {
        return httpClient.postEmpty(Paths.getAddAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddAuthIssuerRequest(url))
        }
    }

    @DoordeckOnly
    override fun deleteAuthIssuer(applicationId: String, url: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getDeleteAuthIssuerPath(applicationId)) {
            addRequestHeaders()
            setBody(DeleteAuthIssuerRequest(url))
        }
    }

    @DoordeckOnly
    override fun addCorsDomain(applicationId: String, url: String): EmptyResponse {
        return httpClient.postEmpty(Paths.getAddCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(AddCorsDomainRequest(url))
        }
    }

    @DoordeckOnly
    override fun removeCorsDomain(applicationId: String, url: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getRemoveCorsDomainPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveCorsDomainRequest(url))
        }
    }

    @DoordeckOnly
    override fun addApplicationOwner(applicationId: String, userId: String): EmptyResponse {
        return httpClient.postEmpty(Paths.getAddApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(AddApplicationOwnerRequest(userId))
        }
    }

    @DoordeckOnly
    override fun removeApplicationOwner(applicationId: String, userId: String): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getRemoveApplicationOwnerPath(applicationId)) {
            addRequestHeaders()
            setBody(RemoveApplicationOwnerRequest(userId))
        }
    }

    @DoordeckOnly
    override fun getApplicationOwnersDetails(applicationId: String): ApplicationOwnerDetailsResponse {
        return httpClient.get(Paths.getApplicationOwnersDetailsPath(applicationId))
    }
}