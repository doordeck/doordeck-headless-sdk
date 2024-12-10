package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl

actual interface HelperResource {
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)
    fun uploadPlatformLogoJson(data: String)

    fun assistedLogin(email: String, password: String): AssistedLoginResponse
    fun assistedLoginJson(data: String): String
}

actual fun helper(): HelperResource = HelperResourceImpl