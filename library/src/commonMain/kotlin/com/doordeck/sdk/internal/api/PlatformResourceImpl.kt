package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.PlatformResource
import com.doordeck.sdk.api.requests.AddApplicationOwnerRequest
import com.doordeck.sdk.api.requests.AddAuthIssuerRequest
import com.doordeck.sdk.api.requests.AddCorsDomainRequest
import com.doordeck.sdk.api.requests.DeleteAuthIssuerRequest
import com.doordeck.sdk.api.requests.RemoveApplicationOwnerRequest
import com.doordeck.sdk.api.requests.RemoveCorsDomainRequest
import com.doordeck.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class PlatformResourceImpl(
    private val httpClient: HttpClient
) : PlatformResource {

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

    override fun deleteApplication(applicationId: String) {
        runBlocking {
            httpClient.delete(Paths.getDeleteApplicationPath(applicationId))
        }
    }

    // TODO upload URL is a presigned S3 url, so we might need to implement a helper function to upload the file, or just create a single public API which gets the upload URL and does the upload in a single go
    override fun getLogoUploadUrl(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun addAuthKey(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun addAuthIssuer(applicationId: String, url: String) {
        runBlocking {
            httpClient.post(Paths.getAddAuthIssuerPath(applicationId)) {
                addRequestHeaders()
                setBody(AddAuthIssuerRequest(url))
            }
        }
    }

    override fun deleteAuthIssuer(applicationId: String, url: String) {
        runBlocking {
            httpClient.delete(Paths.getDeleteAuthIssuerPath(applicationId)) {
                addRequestHeaders()
                setBody(DeleteAuthIssuerRequest(url))
            }
        }
    }

    override fun addCorsDomain(applicationId: String, url: String) {
        runBlocking {
            httpClient.post(Paths.getAddCorsDomainPath(applicationId)) {
                addRequestHeaders()
                setBody(AddCorsDomainRequest(url))
            }
        }
    }

    override fun removeCorsDomain(applicationId: String, url: String) {
        runBlocking {
            httpClient.delete(Paths.getRemoveCorsDomainPath(applicationId)) {
                addRequestHeaders()
                setBody(RemoveCorsDomainRequest(url))
            }
        }
    }

    override fun addApplicationOwner(applicationId: String, userId: String) {
        runBlocking {
            httpClient.post(Paths.getAddApplicationOwnerPath(applicationId)) {
                addRequestHeaders()
                setBody(AddApplicationOwnerRequest(userId))
            }
        }
    }

    override fun removeApplicationOwner(applicationId: String, userId: String) {
        runBlocking {
            httpClient.delete(Paths.getRemoveApplicationOwnerPath(applicationId)) {
                addRequestHeaders()
                setBody(RemoveApplicationOwnerRequest(userId))
            }
        }
    }

    override fun getApplicationOwnersDetails(applicationId: String): ApplicationOwnerDetailsResponse = runBlocking {
        httpClient.get(Paths.getApplicationOwnersDetailsPath(applicationId)).body()
    }
}