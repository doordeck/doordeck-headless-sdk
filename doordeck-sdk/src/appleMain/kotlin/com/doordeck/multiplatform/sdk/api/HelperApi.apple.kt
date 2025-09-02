package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedRegisterEphemeralKeyResponse
import platform.Foundation.NSUUID

/**
 * Platform-specific implementations of helper-related API calls.
 */
actual object HelperApi {
    /**
     * @see HelperClient.uploadPlatformLogoRequest
     */
    @Throws(Exception::class)
    suspend fun uploadPlatformLogo(applicationId: NSUUID, contentType: String, image: ByteArray) = HelperClient
        .uploadPlatformLogoRequest(
            applicationId = applicationId.UUIDString,
            contentType = contentType,
            image = image
        )

    /**
     * @see HelperClient.assistedLoginRequest
     */
    @Throws(Exception::class)
    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse = HelperClient
        .assistedLoginRequest(
            email = email,
            password = password
        )
        .toAssistedLoginResponse()

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    @Throws(Exception::class)
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
    @Throws(Exception::class)
    suspend fun assistedRegister(
        email: String,
        password: String,
        displayName: String? = null,
        force: Boolean = false
    ) = HelperClient.assistedRegisterRequest(
        email = email,
        password = password,
        displayName = displayName,
        force = force
    )
}

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
actual fun helper(): HelperApi = HelperApi