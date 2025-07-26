package com.doordeck.multiplatform.sdk.model.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class AssistedLoginResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

@Serializable
internal data class AssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)