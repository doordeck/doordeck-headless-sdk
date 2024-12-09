package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperClient
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import kotlin.js.Promise

@JsExport
actual interface HelperResource {
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<dynamic>

    fun assistedLogin(email: String, password: String): Promise<AssistedLoginResponse>
}

private val helper = HelperResourceImpl

@JsExport
actual fun helper(): HelperResource = helper