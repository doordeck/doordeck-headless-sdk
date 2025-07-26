package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.data.AssistedLogin
import com.doordeck.multiplatform.sdk.model.data.AssistedRegisterEphemeralKey
import com.doordeck.multiplatform.sdk.model.data.toAssistedLogin
import com.doordeck.multiplatform.sdk.model.data.toAssistedRegisterEphemeralKey
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.security.PrivateKey
import java.security.PublicKey
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of helper-related API calls.
 */
actual object HelperApi {
    /**
     * @see HelperClient.uploadPlatformLogoRequest
     */
    suspend fun uploadPlatformLogo(
        applicationId: UUID,
        contentType: String,
        image: ByteArray
    ) {
        return HelperClient.uploadPlatformLogoRequest(
            applicationId = applicationId.toString(),
            contentType = contentType,
            image = image
        )
    }

    /**
     * Async variant of [HelperApi.uploadPlatformLogo] returning [CompletableFuture].
     */
    fun uploadPlatformLogoAsync(
        applicationId: UUID,
        contentType: String,
        image: ByteArray
    ): CompletableFuture<Unit> {
        return completableFuture {
            uploadPlatformLogo(
                applicationId = applicationId,
                contentType = contentType,
                image = image
            )
        }
    }

    /**
     * @see HelperClient.assistedLoginRequest
     */
    suspend fun assistedLogin(
        email: String,
        password: String
    ): AssistedLogin {
        return HelperClient.assistedLoginRequest(
            email = email,
            password = password
        ).toAssistedLogin()
    }

    /**
     * Async variant of [HelperApi.assistedLogin] returning [CompletableFuture].
     */
    fun assistedLoginAsync(
        email: String,
        password: String
    ): CompletableFuture<AssistedLogin> {
        return completableFuture {
            assistedLogin(
                email = email,
                password = password
            )
        }
    }

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    suspend fun assistedRegisterEphemeralKey(
        publicKey: PublicKey? = null,
        privateKey: PrivateKey? = null
    ): AssistedRegisterEphemeralKey {
        return HelperClient.assistedRegisterEphemeralKeyRequest(
            publicKey = publicKey?.encoded,
            privateKey = privateKey?.encoded
        ).toAssistedRegisterEphemeralKey()
    }

    /**
     * Async variant of [HelperApi.assistedRegisterEphemeralKey] returning [CompletableFuture].
     */
    fun assistedRegisterEphemeralKeyAsync(
        publicKey: PublicKey? = null,
        privateKey: PrivateKey? = null
    ): CompletableFuture<AssistedRegisterEphemeralKey> {
        return completableFuture {
            assistedRegisterEphemeralKey(
                publicKey = publicKey,
                privateKey = privateKey
            )
        }
    }

    /**
     * @see HelperClient.assistedRegisterRequest
     */
    suspend fun assistedRegister(
        email: String,
        password: String,
        displayName: String? = null,
        force: Boolean = false
    ) {
        return HelperClient.assistedRegisterRequest(
            email = email,
            password = password,
            displayName = displayName,
            force = force
        )
    }

    /**
     * Async variant of [HelperApi.assistedRegister] returning [CompletableFuture].
     */
    fun assistedRegisterAsync(
        email: String,
        password: String,
        displayName: String? = null,
        force: Boolean = false
    ): CompletableFuture<Unit> {
        return completableFuture {
            assistedRegister(
                email = email,
                password = password,
                displayName = displayName,
                force = force
            )
        }
    }
}

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
actual fun helper(): HelperApi = HelperApi