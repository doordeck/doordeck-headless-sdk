package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

@JsExport
actual object HelperApi {
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<dynamic> {
        return promise { HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    fun assistedLogin(email: String, password: String): Promise<AssistedLoginResponse> {
        return promise { HelperClient.assistedLoginRequest(email, password) }
    }

    fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null): Promise<AssistedRegisterEphemeralKeyResponse> {
        return promise { HelperClient.assistedRegisterEphemeralKeyRequest(publicKey) }
    }

    fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false): Promise<dynamic> {
        return promise { HelperClient.assistedRegisterRequest(email, password, displayName, force) }
    }
}

private val helper = HelperApi

@JsExport
actual fun helper(): HelperApi = helper