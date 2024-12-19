package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse

internal class HelperResourceImpl(
    private val helperClient: HelperClient
) : HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return helperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    override suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return helperClient.assistedLoginRequest(email, password)
    }

    override suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray?): AssistedRegisterEphemeralKeyResponse {
        return helperClient.assistedRegisterEphemeralKeyRequest(publicKey)
    }

    override suspend fun assistedRegister(email: String, password: String, displayName: String?, force: Boolean) {
        return helperClient.assistedRegisterRequest(email, password, displayName, force)
    }
}