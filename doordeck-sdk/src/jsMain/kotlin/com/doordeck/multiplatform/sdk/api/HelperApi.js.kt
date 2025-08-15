package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedRegisterEphemeralKeyResponse
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
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<dynamic> = promise {
        HelperClient.uploadPlatformLogoRequest(
            applicationId = applicationId,
            contentType = contentType,
            image = image
        )
    }

    /**
     * @see HelperClient.assistedLoginRequest
     */
    fun assistedLogin(email: String, password: String): Promise<AssistedLoginResponse> = promise {
        HelperClient
            .assistedLoginRequest(
                email = email,
                password = password
            )
            .toAssistedLoginResponse()
    }

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    fun assistedRegisterEphemeralKey(
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): Promise<AssistedRegisterEphemeralKeyResponse> = promise {
        HelperClient
            .assistedRegisterEphemeralKeyRequest(
                publicKey = publicKey,
                privateKey = privateKey
            )
            .toAssistedRegisterEphemeralKeyResponse()
    }

    /**
     * @see HelperClient.assistedRegisterRequest
     */
    fun assistedRegister(
        email: String,
        password: String,
        displayName: String? = null,
        force: Boolean = false
    ): Promise<dynamic> = promise {
        HelperClient.assistedRegisterRequest(
            email = email,
            password = password,
            displayName = displayName,
            force = force
        )
    }
}

private val helper = HelperApi

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
@JsExport
actual fun helper(): HelperApi = helper