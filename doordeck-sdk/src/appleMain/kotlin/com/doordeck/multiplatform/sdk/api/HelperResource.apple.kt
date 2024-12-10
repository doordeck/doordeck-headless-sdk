package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl

actual interface HelperResource {
    @Throws(Exception::class)
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)

    @Throws(Exception::class)
    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse
}

actual fun helper(): HelperResource = HelperResourceImpl