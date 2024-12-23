package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse

internal object HelperResourceImpl : HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    override suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
    }
}