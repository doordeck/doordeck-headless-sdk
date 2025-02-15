package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse

actual object HelperApi {
    @Throws(Exception::class)
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    @Throws(Exception::class)
    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
    }

    @Throws(Exception::class)
    suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null): AssistedRegisterEphemeralKeyResponse {
        return HelperClient.assistedRegisterEphemeralKeyRequest(publicKey)
    }

    @Throws(Exception::class)
    suspend fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false) {
        return HelperClient.assistedRegisterRequest(email, password, displayName, force)
    }
}

actual fun helper(): HelperApi = HelperApi