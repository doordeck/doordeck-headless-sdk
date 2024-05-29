package com.doordeck.sdk.api

import kotlin.js.JsExport

@JsExport
interface PlatformResource {

    fun createApplication() // TODO
    fun listApplications() // TODO
    fun getApplication(applicationId: String) // TODO
    fun updateApplication(applicationId: String) // TODO
    fun deleteApplication(applicationId: String) // TODO
    fun getLogoUploadUrl(applicationId: String) // TODO
    fun addAuthKey(applicationId: String) // TODO
    fun addAuthIssuer(applicationId: String) // TODO
    fun deleteAuthIssuer(applicationId: String) // TODO
    fun addCorsDomain(applicationId: String) // TODO
    fun removeCorsDomain(applicationId: String) // TODO
    fun addApplicationOwner(applicationId: String) // TODO
    fun removeApplicationOwner(applicationId: String) // TODO
    fun getApplicationOwnersDetails(applicationId: String) // TODO
}