package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse

internal class HelperResourceImpl(
    private val helperClient: HelperClient
) : HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return helperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    override suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return helperClient.assistedLoginRequest(email, password)
    }
}