package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

/**
 * Platform-specific implementations of helper-related API calls.
 */
@JsExport
actual object HelperApi {
    /**
     * @see HelperClient.uploadPlatformLogoRequest
     */
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<dynamic> {
        return promise { HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    /**
     * @see HelperClient.assistedLoginRequest
     */
    fun assistedLogin(email: String, password: String): Promise<AssistedLoginResponse> {
        return promise { HelperClient.assistedLoginRequest(email, password) }
    }

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null, privateKey: ByteArray? = null): Promise<AssistedRegisterEphemeralKeyResponse> {
        return promise { HelperClient.assistedRegisterEphemeralKeyRequest(publicKey, privateKey) }
    }

    /**
     * @see HelperClient.assistedRegisterRequest
     */
    fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false): Promise<dynamic> {
        return promise { HelperClient.assistedRegisterRequest(email, password, displayName, force) }
    }
}

private val helper = HelperApi

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
@JsExport
actual fun helper(): HelperApi = helper