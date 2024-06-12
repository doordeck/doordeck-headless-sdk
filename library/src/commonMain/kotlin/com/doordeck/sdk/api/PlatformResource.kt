package com.doordeck.sdk.api

import com.doordeck.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.sdk.api.responses.EmptyResponse
import kotlin.js.JsExport

@JsExport
interface PlatformResource {

    fun createApplication() // TODO
    fun listApplications() // TODO
    fun getApplication(applicationId: String) // TODO
    fun updateApplication(applicationId: String) // TODO
    fun deleteApplication(applicationId: String): EmptyResponse
    fun getLogoUploadUrl(applicationId: String) // TODO
    fun addAuthKey(applicationId: String) // TODO
    fun addAuthIssuer(applicationId: String, url: String): EmptyResponse
    fun deleteAuthIssuer(applicationId: String, url: String): EmptyResponse
    fun addCorsDomain(applicationId: String, url: String): EmptyResponse
    fun removeCorsDomain(applicationId: String, url: String): EmptyResponse
    fun addApplicationOwner(applicationId: String, userId: String): EmptyResponse
    fun removeApplicationOwner(applicationId: String, userId: String): EmptyResponse
    fun getApplicationOwnersDetails(applicationId: String): ApplicationOwnerDetailsResponse
}