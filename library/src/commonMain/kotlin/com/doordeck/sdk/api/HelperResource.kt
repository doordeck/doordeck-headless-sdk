package com.doordeck.sdk.api

import kotlin.js.JsExport

@JsExport
interface HelperResource {

    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)
}