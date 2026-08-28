package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.ServerTimeResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toServerTimeResponse

/**
 * Platform-specific implementations of helper-related API calls.
 */
@JsExport
actual object HelperApi {
    /**
     * @see HelperClient.uploadPlatformLogoRequest
     */
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Unit = HelperClient
        .uploadPlatformLogoRequest(
            applicationId = applicationId,
            contentType = contentType,
            image = image
        )

    /**
     * @see HelperClient.assistedLoginRequest
     */
    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse = HelperClient
        .assistedLoginRequest(
            email = email,
            password = password
        )
        .toAssistedLoginResponse()

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    suspend fun assistedRegisterEphemeralKey(
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): AssistedRegisterEphemeralKeyResponse = HelperClient
        .assistedRegisterEphemeralKeyRequest(
            publicKey = publicKey,
            privateKey = privateKey
        )
        .toAssistedRegisterEphemeralKeyResponse()

    /**
     * @see HelperClient.assistedRegisterRequest
     */
    suspend fun assistedRegister(
        email: String,
        password: String,
        displayName: String? = null,
        force: Boolean = false
    ): Unit = HelperClient
        .assistedRegisterRequest(
            email = email,
            password = password,
            displayName = displayName,
            force = force
        )

    suspend fun serverTime(): ServerTimeResponse = HelperClient
        .serverTimeRequest()
        .toServerTimeResponse()
}

private val helper = HelperApi

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
actual fun helper(): HelperApi = helper