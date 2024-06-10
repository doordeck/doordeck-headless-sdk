package com.doordeck.sdk.api

import com.doordeck.sdk.api.responses.ApplicationOwnerDetailsResponse
import kotlin.js.JsExport

@JsExport
interface PlatformResource {

    fun createApplication() // TODO
    fun listApplications() // TODO
    fun getApplication(applicationId: String) // TODO
    fun updateApplication(applicationId: String) // TODO
    fun deleteApplication(applicationId: String)
    fun getLogoUploadUrl(applicationId: String) // TODO
    fun addAuthKey(applicationId: String) // TODO
    fun addAuthIssuer(applicationId: String, url: String)
    fun deleteAuthIssuer(applicationId: String, url: String)
    fun addCorsDomain(applicationId: String, url: String)
    fun removeCorsDomain(applicationId: String, url: String)
    fun addApplicationOwner(applicationId: String, userId: String)
    fun removeApplicationOwner(applicationId: String, userId: String)
    fun getApplicationOwnersDetails(applicationId: String): ApplicationOwnerDetailsResponse
}