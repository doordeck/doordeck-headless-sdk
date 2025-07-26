package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse

data class AssistedLogin(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

data class AssistedRegisterEphemeralKey(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

internal fun AssistedLoginResponse.toAssistedLogin(): AssistedLogin = AssistedLogin(
    requiresVerification = requiresVerification,
    requiresRetry = requiresRetry
)

internal fun AssistedRegisterEphemeralKeyResponse.toAssistedRegisterEphemeralKey(): AssistedRegisterEphemeralKey = AssistedRegisterEphemeralKey(
    requiresVerification = requiresVerification,
    requiresRetry = requiresRetry
)