package com.doordeck.multiplatform.sdk.model.responses

data class AssistedLoginResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

data class AssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

internal fun BasicAssistedLoginResponse.toAssistedLoginResponse(): AssistedLoginResponse = AssistedLoginResponse(
    requiresVerification = requiresVerification,
    requiresRetry = requiresRetry
)

internal fun BasicAssistedRegisterEphemeralKeyResponse.toAssistedRegisterEphemeralKeyResponse(): AssistedRegisterEphemeralKeyResponse =
    AssistedRegisterEphemeralKeyResponse(
        requiresVerification = requiresVerification,
        requiresRetry = requiresRetry
    )