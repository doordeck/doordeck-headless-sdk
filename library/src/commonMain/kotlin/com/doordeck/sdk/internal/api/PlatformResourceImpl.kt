package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.PlatformResource
import io.ktor.client.*

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
        TODO("Not yet implemented")
    }

    override fun getLogoUploadUrl(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun addAuthKey(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun addAuthIssuer(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAuthIssuer(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun addCorsDomain(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun removeCorsDomain(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun addApplicationOwner(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun removeApplicationOwner(applicationId: String) {
        TODO("Not yet implemented")
    }

    override fun getApplicationOwnersDetails(applicationId: String) {
        TODO("Not yet implemented")
    }
}