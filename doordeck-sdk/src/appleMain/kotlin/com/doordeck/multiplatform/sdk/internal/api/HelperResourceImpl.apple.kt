package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse

internal object HelperResourceImpl : HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    override suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
    }

    override suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray?): AssistedRegisterEphemeralKeyResponse {
        return HelperClient.assistedRegisterEphemeralKeyRequest(publicKey)
    }

    override suspend fun assistedRegister(email: String, password: String, displayName: String?, force: Boolean) {
        return HelperClient.assistedRegisterRequest(email, password, displayName, force)
    }
}