package com.doordeck.multiplatform.sdk.model.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class BasicAssistedLoginResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

@Serializable
internal data class BasicAssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)