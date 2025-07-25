package com.doordeck.multiplatform.sdk.model.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class NetworkAssistedLoginResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

@Serializable
internal data class NetworkAssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)