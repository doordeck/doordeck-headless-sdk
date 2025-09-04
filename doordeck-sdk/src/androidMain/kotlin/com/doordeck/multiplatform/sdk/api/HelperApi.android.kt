package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.io.InputStream
import java.security.KeyPair
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of helper-related API calls.
 */
actual object HelperApi {
    /**
     * @see HelperClient.uploadPlatformLogoRequest
     */
    suspend fun uploadPlatformLogo(applicationId: UUID, contentType: String, image: InputStream) = HelperClient
        .uploadPlatformLogoRequest(
            applicationId = applicationId.toString(),
            contentType = contentType,
            image = image.readBytes()
        )

    /**
     * Async variant of [HelperApi.uploadPlatformLogo] returning [CompletableFuture].
     */
    fun uploadPlatformLogoAsync(
        applicationId: UUID,
        contentType: String,
        image: InputStream
    ): CompletableFuture<Unit> = completableFuture {
        uploadPlatformLogo(
            applicationId = applicationId,
            contentType = contentType,
            image = image
        )
    }

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
     * Async variant of [HelperApi.assistedLogin] returning [CompletableFuture].
     */
    fun assistedLoginAsync(
        email: String,
        password: String
    ): CompletableFuture<AssistedLoginResponse> = completableFuture {
        assistedLogin(
            email = email,
            password = password
        )
    }

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    @JvmOverloads
    suspend fun assistedRegisterEphemeralKey(
        keyPair: KeyPair? = null
    ): AssistedRegisterEphemeralKeyResponse = HelperClient
        .assistedRegisterEphemeralKeyRequest(
            publicKey = keyPair?.public?.encoded,
            privateKey = keyPair?.private?.encoded
        )
        .toAssistedRegisterEphemeralKeyResponse()

    /**
     * Async variant of [HelperApi.assistedRegisterEphemeralKey] returning [CompletableFuture].
     */
    @JvmOverloads
    fun assistedRegisterEphemeralKeyAsync(
        keyPair: KeyPair? = null
    ): CompletableFuture<AssistedRegisterEphemeralKeyResponse> = completableFuture {
        assistedRegisterEphemeralKey(keyPair)
    }

    /**
     * @see HelperClient.assistedRegisterRequest
     */
    @JvmOverloads
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

    /**
     * Async variant of [HelperApi.assistedRegister] returning [CompletableFuture].
     */
    @JvmOverloads
    fun assistedRegisterAsync(
        email: String,
        password: String,
        displayName: String? = null,
        force: Boolean = false
    ): CompletableFuture<Unit> = completableFuture {
        assistedRegister(
            email = email,
            password = password,
            displayName = displayName,
            force = force
        )
    }
}

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
actual fun helper(): HelperApi = HelperApi